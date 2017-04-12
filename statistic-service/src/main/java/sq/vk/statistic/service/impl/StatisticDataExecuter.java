package sq.vk.statistic.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import sq.vk.statistic.domain.PokerRoomType;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
class StatisticDataExecuter {

  private static final String USER_AGENT = "Mozilla/5.0";

  String getHtmlViewForUserAndRoom(String name, PokerRoomType type) throws IOException {

    final String uri = new StringBuilder("https://www.sharkscope.com/#Player-Statistics//networks/").append(
      type.getValue()).append("/players/").append(name).toString();

    HttpClient client = HttpClientBuilder.create().build();

    HttpGet getRequest = new HttpGet(uri);
    getRequest.addHeader("User-Agent", USER_AGENT);

    HttpResponse response = client.execute(getRequest);

    BufferedReader rd = new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()));

    StringBuffer result = new StringBuffer();
    String line = "";

    while ((line = rd.readLine()) != null) {
      result.append(line);
    }

    return result.toString();
  }
}
