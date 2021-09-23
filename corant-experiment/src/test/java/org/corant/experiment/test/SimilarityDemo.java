package org.corant.experiment.test;

import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.DiceTextSimilarity;
import org.xm.similarity.text.EditDistanceSimilarity;
import org.xm.similarity.text.EuclideanDistanceTextSimilarity;
import org.xm.similarity.text.JaccardTextSimilarity;
import org.xm.similarity.text.JaroDistanceTextSimilarity;
import org.xm.similarity.text.ManhattanDistanceTextSimilarity;
import org.xm.similarity.text.SimHashPlusHammingDistanceTextSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.util.Locale;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/6/16
 * @since
 */
public class SimilarityDemo {

  public static void main(String[] args) {
    //    String word1 = "Engine Vehicle Engines 发动机";
    //    String word2 = "MOUNTING PLATE LEFT";
    //
    //    String word1 = "MOUNTING PLATE LEFT".toLowerCase(Locale.ROOT);

    //    String word2 = "Upper spring plate 弹簧座 上部";
    //
    //    String word1 = "Spring pocket 弹簧座";

    //    String word2 = "Roof moulding painted right 装饰条 车顶 已上漆 右";
    //
    //    String word1 = "trim panel right, painted 饰板 已上漆 右";

    //    String word2 = "CURTAIN RIGHT 窗帘 右";
    //
    //    String word1 = "RUBBER STRIP RIGHT 护条 右";
    //
    //    String word2 =
    //        "Exch. radio BMW Prof. CD/IBOC/SDARS".toLowerCase(Locale.ROOT)
    //            + " 保修 收音机 BMW Prof. CD/IBOC/SDARS";
    //
    //    String word1 =
    //        "Radio BMW Professional MD".toLowerCase(Locale.ROOT) + " 收音机 BMW Professional MD";
    //
    //    String word2 = "Bracket".toLowerCase(Locale.ROOT) + " 定位板";
    //
    //    String word1 = "Gasket".toLowerCase(Locale.ROOT) + " 密封件";

    //    String word2 = "SPRAY NOZZLE LEFT".toLowerCase(Locale.ROOT) + " 喷嘴 左";
    //
    //    String word1 = "Covering left".toLowerCase(Locale.ROOT) + " 饰板 左";

    //    String word2 = "IGNITION WIRE 1 点火线 1";
    //
    //    String word1 = "IGNITION WIRE 点火线";

    //    String word2 = "Gasket 密封件";
    //
    //    String word1 = "Gasket ring 密封环";

    //    String word2 =
    //        "DESIGNF348888;EEEEN577777;330dxDrive;000020140301;9999NULL".toLowerCase(Locale.ROOT);
    //
    //    String word1 =
    //
    // "DESIGNF348888;EEEEN577777;330dXdrive;000020140303;999920130700".toLowerCase(Locale.ROOT);
    //
    String word2 = "Hose".toLowerCase(Locale.ROOT) + " 成型软管";

    String word1 = "Hose".toLowerCase(Locale.ROOT) + " 软管";

    //        String word2 = "DESIGNG558888;EEEEB387777;1.5 220 i;000020171000;9999NULL";
    //
    //        String word1 = "DESIGNF558888;EEEEB387777;One;000020140200;999920151200";

    TextSimilarity similarity = new CosineSimilarity();
    DiceTextSimilarity diceTextSimilarity = new DiceTextSimilarity();
    EditDistanceSimilarity editDistanceSimilarity = new EditDistanceSimilarity();
    EuclideanDistanceTextSimilarity euclideanDistanceTextSimilarity =
        new EuclideanDistanceTextSimilarity();
    JaccardTextSimilarity jaccardTextSimilarity = new JaccardTextSimilarity();
    JaroDistanceTextSimilarity jaroDistanceTextSimilarity = new JaroDistanceTextSimilarity();
    ManhattanDistanceTextSimilarity manhattanDistanceTextSimilarity =
        new ManhattanDistanceTextSimilarity();
    SimHashPlusHammingDistanceTextSimilarity simHashPlusHammingDistanceTextSimilarity =
        new SimHashPlusHammingDistanceTextSimilarity();

    double score1pk2 = similarity.getSimilarity(word1, word2);
    double diceTextSimilaritySimilarity = diceTextSimilarity.getSimilarity(word1, word2);
    double editDistanceSimilaritySimilarity = editDistanceSimilarity.getSimilarity(word1, word2);
    double euclideanDistanceTextSimilaritySimilarity =
        euclideanDistanceTextSimilarity.getSimilarity(word1, word2);
    double jaccardTextSimilaritySimilarity = jaccardTextSimilarity.getSimilarity(word1, word2);
    double jaroDistanceTextSimilaritySimilarity =
        jaroDistanceTextSimilarity.getSimilarity(word1, word2);
    double manhattanDistanceTextSimilaritySimilarity =
        manhattanDistanceTextSimilarity.getSimilarity(word1, word2);
    double simHashPlusHammingDistanceTextSimilaritySimilarity =
        simHashPlusHammingDistanceTextSimilarity.getSimilarity(word1, word2);
    System.out.println("word1 = " + word1);
    System.out.println("word2 = " + word2);
    System.out.println("score1pk2 = " + score1pk2);
    System.out.println("diceTextSimilaritySimilarity = " + diceTextSimilaritySimilarity);
    System.out.println("editDistanceSimilaritySimilarity = " + editDistanceSimilaritySimilarity);
    System.out.println(
        "euclideanDistanceTextSimilaritySimilarity = " + euclideanDistanceTextSimilaritySimilarity);
    System.out.println("jaccardTextSimilartitySimilarity = " + jaccardTextSimilaritySimilarity);
    System.out.println(
        "jaroDistanceTextSimilaritySimilarity = " + jaroDistanceTextSimilaritySimilarity);
    System.out.println(
        "manhattanDistanceTextSimilaritySimilarity = " + manhattanDistanceTextSimilaritySimilarity);
    System.out.println(
        "simHashPlusHammingDistanceTextSimilaritySimilarity = "
            + simHashPlusHammingDistanceTextSimilaritySimilarity);
  }
}
