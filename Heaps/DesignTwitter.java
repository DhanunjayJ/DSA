import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DesignTwitter {
    class Tweet {
    int id;
    int time;
    Tweet next;

    Tweet(int id,int time){
        this.id = id;
        this.time = time;
    }
}

    class Twitter {
    private static int timeStamp = 0;
    private Map<Integer, Set<Integer>> following;
    private Map<Integer, Tweet> userTweets;

    public Twitter() {
        following = new HashMap<>();
        userTweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        // Ensure user follows themselves to see their own tweets
        if (!following.containsKey(userId)) follow(userId, userId);
        
        Tweet newTweet = new Tweet(tweetId, timeStamp++);
        newTweet.next = userTweets.get(userId); // Link new tweet to old head
        userTweets.put(userId, newTweet); // Set new tweet as head
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.time - a.time);
        
        Set<Integer> fers = following.get(userId);
        if (fers == null) return new ArrayList<>();

        for (int fId : fers) {
            Tweet t = userTweets.get(fId);
            if (t != null) pq.add(t);
        }

        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty() && res.size() < 10) {
            Tweet t = pq.poll();
            res.add(t.id);
            if (t.next != null) pq.add(t.next);
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) {
        following.computeIfAbsent(followerId, k -> new HashSet<>()).add(followerId);
        following.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        // Prevent users from unfollowing themselves
        if (following.containsKey(followerId) && followerId != followeeId) {
            following.get(followerId).remove(followeeId);
        }
    }
}
}
