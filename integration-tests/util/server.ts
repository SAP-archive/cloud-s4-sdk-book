import app = require('../../app');
import { AddressInfo, Server } from 'net';

export function getUrl(server: Server): string {
  const address = server.address() as AddressInfo;
  return `http://127.0.0.1:${address.port}`;
}

export async function startServer(): Promise<Server> {
  return new Promise((resolve) => {
    const server = app.listen(() => {
      resolve(server);
    });
  });
}

export async function stopServer(server: Server): Promise<void> {
  return new Promise((resolve) => {
    server.close(() => {
      resolve();
    });
  });
}
