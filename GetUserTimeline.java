import twitter4j.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

public class GetUserTimeline {
	public static void main(String[] args) {
		final String charset = "UTF-8";

		System.out.println("Please input user's screen name to read: ");
		Scanner sc = new Scanner(System.in);
		final String user = sc.nextLine();
		final String filename = user + ".txt";
		sc.close();

		Twitter twitter = new TwitterFactory().getInstance();
		List<Status> tweets;

		try (PrintWriter writer = new PrintWriter(filename, charset)) {
			tweets = twitter.getUserTimeline(user, new Paging(10, 200));
			writer.println("Showing @" + user + "'s user timeline.");
			for (Status tweet : tweets) {
				writer.println(tweet.getCreatedAt() + "  @"
						+ tweet.getUser().getScreenName() + " -- "
						+ tweet.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		} catch (FileNotFoundException e) {
			System.out.println("Could not create or open file " + filename);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported charset: " + charset);
		}
	}
}
