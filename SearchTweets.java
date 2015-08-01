import twitter4j.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

public class SearchTweets {
	public static void main(String[] args) {
		final String filename = "search.txt";
		final String charset = "UTF-8";

		System.out.println("Please input your query string: ");
		Scanner sc = new Scanner(System.in);
		final String queryString = sc.nextLine();
		sc.close();

		Twitter twitter = new TwitterFactory().getInstance();
		try (PrintWriter writer = new PrintWriter(filename, charset)) {
			writer.println("Your query string was: " + queryString);
			Query query = new Query(queryString);
			query.lang("zh");
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					writer.println(tweet.getCreatedAt() + "  @"
							+ tweet.getUser().getScreenName() + " -- "
							+ tweet.getText());
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		} catch (FileNotFoundException e) {
			System.out.println("Could not create or open file " + filename);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported charset: " + charset);
		}
	}
}
