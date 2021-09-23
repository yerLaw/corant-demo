package org.corant.experiment.bmw;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.corant.Corant;
import org.corant.modules.json.Jsons;
import org.corant.modules.query.QueryRuntimeException;
import org.corant.shared.util.Texts;

import java.io.File;
import java.util.Map;
import java.util.stream.Stream;

import static org.corant.context.Beans.findNamed;
import static org.corant.shared.util.Assertions.shouldNotNull;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/8/24
 * @since
 */
public class PartLinkage {
  public static void main(String[] args) {
    Corant.run(
        () -> {
          MongoDatabase mongoDatabase =
              findNamed(MongoDatabase.class, shouldNotNull("bmw.CADB-DATA"))
                  .orElseThrow(QueryRuntimeException::new);
          Stream<String> lines =
              Texts.lines(
                  new File(
                      "E:\\file_file_file\\bmw_part_linkage\\宝马OE号与图片的对应关系\\bmw_oe_photo_mapping.json"));
          lines
              .skip(1067582L)
              .forEach(
                  s -> {
                    Map<String, Object> map = Jsons.fromString(s);
                    mongoDatabase
                        .getCollection("bmw_oe_photo_mapping")
                        .insertOne(new Document(map));
                  });
        });
  }
}
