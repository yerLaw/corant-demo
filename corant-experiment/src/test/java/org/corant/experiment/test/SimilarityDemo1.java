package org.corant.experiment.test;

import org.xm.similarity.text.JaroDistanceTextSimilarity;
import org.xm.similarity.text.TextSimilarity;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/6/16
 * @since
 */
public class SimilarityDemo1 {

  public static void main(String[] args) {
    String text1 = "我爱购物";
    String text2 = "我爱读书";
    String text3 = "他是黑客";
    TextSimilarity textSimilarity = new JaroDistanceTextSimilarity();
    double score1pk1 = textSimilarity.getSimilarity(text1, text1);
    double score1pk2 = textSimilarity.getSimilarity(text1, text2);
    double score1pk3 = textSimilarity.getSimilarity(text1, text3);
    double score2pk2 = textSimilarity.getSimilarity(text2, text2);
    double score2pk3 = textSimilarity.getSimilarity(text2, text3);
    double score3pk3 = textSimilarity.getSimilarity(text3, text3);
    System.out.println(text1 + " 和 " + text1 + " 的相似度分值：" + score1pk1);
    System.out.println(text1 + " 和 " + text2 + " 的相似度分值：" + score1pk2);
    System.out.println(text1 + " 和 " + text3 + " 的相似度分值：" + score1pk3);
    System.out.println(text2 + " 和 " + text2 + " 的相似度分值：" + score2pk2);
    System.out.println(text2 + " 和 " + text3 + " 的相似度分值：" + score2pk3);
    System.out.println(text3 + " 和 " + text3 + " 的相似度分值：" + score3pk3);
  }
}
