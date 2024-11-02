package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class SocialNetworkImpl implements SocialNetwork {

    private Set<UserProfile> userProfiles;
    private Set<Post> posts;

    public SocialNetworkImpl() {
        userProfiles = new HashSet<>();
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
        if (!userProfiles.contains(userProfile)) {
            throw new UserRegistrationException("User profile is not registered!");
        }
        if (userProfile == null) {
            throw new IllegalArgumentException("User is null!");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content is empty!");
        }



        Post post = new SocialFeedPost(userProfile, content);
        posts.add(post);
        return post;


    }

    @Override
    public Collection<Post> getPosts() {
       return Collections.unmodifiableCollection(posts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {

       Set<UserProfile> reachedUsers;
       UserProfile autor = post.getAuthor();

       for(var user : userProfiles) {

       }
       

    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2) throws UserRegistrationException {
        return Set.of();
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        return null;
    }
}
