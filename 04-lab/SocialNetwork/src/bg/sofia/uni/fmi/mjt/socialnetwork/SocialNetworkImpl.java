package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.ProfilesSortedByFriendsCount;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SocialNetworkImpl implements SocialNetwork {

    private final Set<UserProfile> userProfiles;
    private Set<Post> posts;

    public SocialNetworkImpl() {
        userProfiles = new HashSet<>();
        posts = new HashSet<>();
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User is null!");
        }
        if (userProfiles.contains(userProfile)) {
            throw new UserRegistrationException("User already registered!");
        }

        userProfiles.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Collections.unmodifiableSet(userProfiles);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User is null!");
        }
        if (content == null || content.equals("")) {
            throw new IllegalArgumentException("Content is empty!");
        }
        if (!userProfiles.contains(userProfile)) {
            throw new UserRegistrationException("User profile is not registered!");
        }

        Post post = new SocialFeedPost(userProfile, content);
        posts.add(post);
        return post;

    }

    @Override
    public Collection<Post> getPosts() {
        return Collections.unmodifiableCollection(posts);
    }

    private boolean hasCommonInterest(UserProfile user1, UserProfile user2) {
        return !Collections.disjoint(user1.getInterests(), user2.getInterests());
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("No post found");
        }

        Set<UserProfile> reachedUsers = new HashSet<>();
        UserProfile author = post.getAuthor();

        Set<UserProfile> visited = new HashSet<>();
        Queue<UserProfile> queue = new LinkedList<>();

        queue.add(author);
        visited.add(author);

        while (!queue.isEmpty()) {
            UserProfile currentUser = queue.poll();

            if (!currentUser.equals(author) && hasCommonInterest(currentUser, author)) {
                reachedUsers.add(currentUser);
            }

            for (UserProfile friend : currentUser.getFriends()) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.add(friend);
                }
            }
        }

        return reachedUsers;

    }

    //     * @throws UserRegistrationException if any of the user profiles is not registered
//     * @throws IllegalArgumentException  if any of the user profiles is null
    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
        throws UserRegistrationException {
        if (userProfile1 == null) {
            throw new IllegalArgumentException("Users are null");
        }
        if (userProfile2 == null) {
            throw new IllegalArgumentException("Users are null");
        }
        if (!userProfiles.contains(userProfile1) || !userProfiles.contains(userProfile2)) {
            throw new UserRegistrationException("User profiles are not registered!");
        }

        Set<UserProfile> mutualFriends = new HashSet<>(userProfile1.getFriends());
        mutualFriends.retainAll(userProfile2.getFriends());

        return mutualFriends;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        SortedSet<UserProfile> sortedProfiles = new TreeSet<>(new ProfilesSortedByFriendsCount());
        sortedProfiles.addAll(userProfiles);
        return sortedProfiles;
    }
}
