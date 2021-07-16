package org.corant.aliyun.oss.demo;

import org.corant.modules.cloud.alibaba.oss.OSSStorageService;
import org.corant.shared.util.Resources;
import org.corant.shared.util.Retry;
import org.corant.shared.util.Texts;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/7/15
 * @since
 */
@ApplicationScoped
public class AliyunFileUpload {
  @Inject
  @Named("parts360-docs")
  OSSStorageService storageService;

  @Inject Logger logger;

  private static String getFmtName(File file) throws IOException {
    ImageInputStream iis = ImageIO.createImageInputStream(file);
    Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
    if (!iter.hasNext()) {
      throw new RuntimeException(file.getName() + ",file No readers found!");
    }
    ImageReader reader = iter.next();
    return reader.getFormatName();
  }

  public void upload() {
    final File uri =
        new File(
            "\\\\192.168.100.210\\gixon\\Server_Data\\192.168.100.250\\TD_BACK_UP\\201904UploadUri.txt");
    try (FileInputStream fis = new FileInputStream(uri);
        Stream<String> stringStream = Texts.lines(fis, 11537, -1)) {

      stringStream.forEach(
          s -> {
            String[] split = s.split(";");
            String brdIdDir = split[0];
            String fileId = split[1];
            File file =
                new File(
                    "\\\\192.168.100.210\\gixon\\Server_Data\\192.168.100.250\\TD_BACK_UP\\Docs\\"
                        + brdIdDir
                        + "\\"
                        + fileId);
            Retry.execute(
                32,
                Duration.ofSeconds(32L),
                () -> {
                  try {
                    doUpload(brdIdDir, fileId, getFmtName(file), file);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  return null;
                });
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void doUpload(String brdId, String fileId, String fmt, File file) {
    String store =
        storageService.store(
            fileId, new Resources.FileSystemResource(file), "brdId", brdId, "fmt", fmt);
    logger.info("upload===============" + brdId + ";" + store);
  }
}
