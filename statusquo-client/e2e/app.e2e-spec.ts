import {StatusQuoClientPage} from "./app.po";

describe('status-quo-client App', () => {
  let page: StatusQuoClientPage;

  beforeEach(() => {
    page = new StatusQuoClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
