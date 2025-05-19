import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, NumberField, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService} from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect } from 'react';

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
        const id_genero = parseInt(genero.value)+1;
        const id_album = parseInt(album.value)+1;
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
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
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

  let listaTipo= useSignal<String[]>([]);
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

//LISTA DE CancionS
export default function CancionView() {
  
  const dataProvider = useDataProvider<Cancion>({ 
    list: () => CancionService.listCancion(),
  });

  function indexIndex({model}:{model:GridItemModel<Cancion>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn  renderer={indexIndex} header="Nro" />
        <GridColumn path="nombre" header="Cancion" />
        <GridColumn path="genero" header="Genero"/>
        <GridColumn path="album" header="Album"/>
        <GridColumn path="duracion" header="Duracion"/>
        <GridColumn path="tipo" header="Tipo"/>
        <GridColumn path="url" header="URL">

        </GridColumn>
      </Grid>
    </main>
  );
}
