import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, Item, NumberField, TextField, VerticalLayout, GridSortColumn, HorizontalLayout, Icon, Select } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useEffect, useState, useCallback } from 'react';
import { _items } from '@vaadin/hilla-lit-form';
import Cancion from 'Frontend/generated/org/unl/music/base/models/Cancion';
import { data } from 'react-router';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Cancion',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};
//GUARDAR Cancion
function CancionEntryForm(props: CancionEntryFormProps) {
  const nombre = useSignal('');
  const genero = useSignal('');
  const album = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value) + 1;
        const id_album = parseInt(album.value) + 1;
        console.log("Guardando canción:", { nombre, id_genero, duracion, url, tipo, id_album });
        await CancionService.createCancion(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      }else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (e: any) {
      console.error("Error al guardar canción:", e.message);
      alert("Error al guardar canción: " + e.message);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumGenero().then(data =>
      listaGenero.value = data
    );
  }, []);

  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumGombo().then(data =>
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      listaTipo.value = data
    );
  }, []);

  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Candelar
            </Button>
            <Button onClick={createCancion} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre de la cancion"
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre de la cansion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero"
            items={listaGenero.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
          <ComboBox label="ALbum"
            items={listaAlbum.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
          <ComboBox label="Tipo"
            items={listaTipo.value}
            placeholder='Seleccione un tipo'
            aria-label='Seleccione un tipo de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <NumberField label="Duracion"
            placeholder='Ingrese la duracion de la cancion'
            aria-label='Ingrese la duracion de la cancion '
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="URL"
            placeholder='Inserte el URL de la cancion'
            aria-label='INserte el URL de la cancion'
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Agregar
      </Button>
    </>
  );
}

//LISTA DE Canciones
export default function CancionView() {
  const [items, setItems] = useState([]);
  //useEffect(() =>{
  const callData = () => {
    CancionService.listAll().then(function (data) {
      //items.values = data;
      setItems(data);
    });
  };
  useEffect(() => {
    callData();
  }, []);
  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    // Custom logic based on the sorting direction
    console.log(`Sort direction changed for column ${columnId} to ${direction}`);

    var dir = (direction == 'asc') ? 1 : 2;
    CancionService.order(columnId, dir).then(function (data) {
      setItems(data);
    });
  }
  function indexIndex({ model }: { model: GridItemModel<Cancion> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }
  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {

      label: 'Cancion',
      value: 'nombre',
    },
    {
      label: 'Album',
      value: 'album',
    },
    {

      label: 'Genero',
      value: 'genero',
    },

  ]
  const search = async () => {
    try {
      CancionService.search(criterio.value, texto.value, 0).then(function (data) { 
        setItems(data);
      });
      criterio.value = '';
      texto.value = '';
      Notification.show('busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={callData}/>
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select items={itemSelect}
          value={criterio.value}
          onValueChanged={(evt) => (criterio.value = evt.detail.value)}
          placeholder="seleccione criterio">

        </Select>
        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={texto.value}
          onValueChanged={(evt) => (texto.value = evt.detail.value)}

        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          Buscar
        </Button>
      </HorizontalLayout>

      <Grid items={items}>
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridSortColumn path="nombre" header="Cancion" onDirectionChanged={(e) => order(e, 'nombre')} />
        <GridSortColumn onDirectionChanged={(e) => order(e, 'genero')} path="genero" header="Genero" />
        <GridSortColumn onDirectionChanged={(e) => order(e, 'album')} path="album" header="Album" />
        <GridSortColumn path="duracion" header="Duracion"onDirectionChanged={(e) => order(e, "duracion")}/>
        <GridColumn path="tipo" header="Tipo" />
        <GridColumn path="url" header="URL">

        </GridColumn>

      </Grid>
    </main>
  );
}