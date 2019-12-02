import { Constructable, Entity } from "@sap/cloud-sdk-core";

export async function getSizeOfUnfilteredResult<EntityT extends Entity>(entityConstructor: Constructable<EntityT>, destination): Promise<number> {
  const businesspartners = await (entityConstructor.requestBuilder() as any)
    .getAll()
    .execute(destination);

  return businesspartners.length;
}

export function expectPropertiesNotNilForAll<EntityT extends Entity>(entities: EntityT[], ...properties: Array<keyof EntityT>) {
  entities.forEach(e => {
    properties.forEach(p => {
      expect(e[p]).not.toBeNil();
    });
  });
}

export function expectPropertiesNilForAll<EntityT extends Entity>(entities: EntityT[], ...properties: Array<keyof EntityT>) {
  entities.forEach(e => {
    properties.forEach(p => {
      expect(e[p]).toBeNil();
    });
  });
}

export function expectPropertiesDefinedForAll<EntityT extends Entity>(entities: EntityT[], ...properties: Array<keyof EntityT>) {
  entities.forEach(e => {
    properties.forEach(p => {
      expect(e[p]).toBeDefined();
    });
  });
}

export function expectPropertiesUndefinedForAll<EntityT extends Entity>(entities: EntityT[], ...properties: Array<keyof EntityT>) {
  entities.forEach(e => {
    properties.forEach(p => {
      expect(e[p]).toBeUndefined();
    });
  });
}

export function expectProperyEqualsForAll<EntityT extends Entity>(entities: EntityT[], property: keyof EntityT, value) {
  entities.forEach(e => {
    expect(e[property]).toEqual(value);
  });
}
