package sq.vk.service.util;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.select.Elements;
import sq.vk.dto.item.ItemDto;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public class LamodaItemHelper implements Function<Map<String, Elements>, ItemDto> {

  private final static String INFO_PREFIX = "<script type=\"text/javascript\" data-module=\"statistics\">;(function(LMDA, d, n){ d[\"product\"] = ";
  private final static String INFO_POSTFIX = "; LMDA[n] = LMDA[n] || []; return LMDA[n].push(d); })(window.LMDA = window.LMDA || {}, {}, 'PAGEDATA');</script>";

  @Override
  public ItemDto apply(Map<String, Elements> elements) {

    String itemName = elements.get("name").text();
    String itemJsonInfo = elements.get("info").toString().replace(INFO_PREFIX, "").replace(INFO_POSTFIX, "");
    String itemPrice = elements.get("price").text();
    String itemSize = "";

    Pattern pattern = Pattern.compile("\\d+.?\\d+");
    Matcher matcher = pattern.matcher(itemPrice);

    if (matcher.find()) {
      itemPrice = matcher.group();
    }

    JSONObject object = new JSONObject(itemJsonInfo);

    if(object.get("isInStock").toString().equals("true")){

      JSONObject size = new JSONObject(object.getJSONObject("size").toString());

      itemSize = size.get("sizes").toString();
    }

    return new ItemDto.Builder().setName(itemName).setPrice(Double.parseDouble(itemPrice)).setSize(itemSize).build();
  }

}
