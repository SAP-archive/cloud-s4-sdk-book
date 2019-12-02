import { expectPropertiesDefinedForAll } from "../util/expect";
import { getUrl, startServer, stopServer } from "../util/server";
import { Yy1_Bpsocialmedia } from "./odata-client/yy-1-bpsocialmedia-cds-service/Yy1_Bpsocialmedia";
import { Yy1_Socialmediaaccount_Bpso000 } from "./odata-client/yy-1-bpsocialmedia-cds-service/Yy1_Socialmediaaccount_Bpso000";

describe('social media', () => {
  const SOCIAL_MEDIA_UUID = '00163e30-2f0c-1ee8-a5af-cf6c35b4a827';

  let server;
  let destination;
  beforeAll(async () => {
    server = await startServer();
    destination = { url: getUrl(server) };
  });

  afterAll(async () => {
    return stopServer(server);
  });

  it('get all with select', async () => {
    const socialMediaList = await Yy1_Bpsocialmedia.requestBuilder()
    .getAll()
    .select(
      Yy1_Bpsocialmedia.BUSINESS_PARTNER,
      Yy1_Bpsocialmedia.TO_SOCIAL_MEDIA_ACCOUNT.select(
        Yy1_Socialmediaaccount_Bpso000.SERVICE,
        Yy1_Socialmediaaccount_Bpso000.ACCOUNT
      ))
    .execute(destination);

    expect(socialMediaList).not.toBeEmpty();

    const socialMediaAccounts = socialMediaList[0].toSocialMediaAccount;

    expectPropertiesDefinedForAll(socialMediaList, 'businessPartner', 'toSocialMediaAccount');
    expectPropertiesDefinedForAll(socialMediaAccounts, 'service', 'account');
  });

  it('get by key with select', async () => {
    const socialMediaInstance = await Yy1_Bpsocialmedia.requestBuilder()
    .getByKey(SOCIAL_MEDIA_UUID)
    .select(
      Yy1_Bpsocialmedia.BUSINESS_PARTNER,
      Yy1_Bpsocialmedia.TO_SOCIAL_MEDIA_ACCOUNT.select(
        Yy1_Socialmediaaccount_Bpso000.SERVICE,
        Yy1_Socialmediaaccount_Bpso000.ACCOUNT
      ))
    .execute(destination);

    expect(socialMediaInstance).not.toBeNil();
    expect(socialMediaInstance.businessPartner).toBeDefined();
    expect(socialMediaInstance.toSocialMediaAccount).toBeDefined();
  });
});
