import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Cancion_1 from "./org/unl/music/base/models/Cancion.js";
async function createCancion_1(nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: string | undefined, id_album: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "createCancion", { nombre, id_genero, duracion, url, tipo, id_album }, init); }
async function listAlbumGenero_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listAlbumGenero", {}, init); }
async function listAlbumGombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listAlbumGombo", {}, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listAll", {}, init); }
async function listAlla_1(init?: EndpointRequestInit_1): Promise<Array<Cancion_1 | undefined> | undefined> { return client_1.call("CancionService", "listAlla", {}, init); }
async function listTipo_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("CancionService", "listTipo", {}, init); }
async function order_1(attribute: string | undefined, type: number | undefined, init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "order", { attribute, type }, init); }
async function search_1(attribute: string | undefined, text: string | undefined, type: number | undefined, init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "search", { attribute, text, type }, init); }
export { createCancion_1 as createCancion, listAlbumGenero_1 as listAlbumGenero, listAlbumGombo_1 as listAlbumGombo, listAll_1 as listAll, listAlla_1 as listAlla, listTipo_1 as listTipo, order_1 as order, search_1 as search };
