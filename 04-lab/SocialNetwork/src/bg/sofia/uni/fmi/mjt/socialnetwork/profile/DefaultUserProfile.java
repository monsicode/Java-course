package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultUserProfile implements UserProfile {

    private final String username;
    Set<Interest> interestsUser;
    Set<UserProfile> friends;

    //later for the bigger class
    //private final Map<UserProfile, List<UserProfile>> friends = null;

    public DefaultUserProfile(String username) {
        this.username = username;
        friends = new HashSet<>();
        interestsUser = new HashSet<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Collections.unmodifiableCollection(interestsUser);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("No value in interest to be add");
        }

        if (interestsUser.contains(interest)) {
            return false;
        } else {
            return interestsUser.add(interest);
        }
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("No value in interest to be add");
        }

        if (!interestsUser.contains(interest)) {
            return false;
        } else {
            return interestsUser.remove(interest);
        }
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Collections.unmodifiableCollection(friends);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if (userProfile == null || this.equals(userProfile)) {
            throw new IllegalArgumentException("The user is trying to add themselves as a friend or the user is null!");
        }

        if (friends.contains(userProfile)) {
            return false;
        } else {
            friends.add(userProfile);
            userProfile.addFriend(this);
            return true;
        }

    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("The user is trying to add themselves as a friend or the user is null!");
        }

        if (!friends.contains(userProfile)) {
            return false;
        } else {
            friends.remove(userProfile);
            userProfile.unfriend(this);
            return true;
        }
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("This user profile is null! ");
        }

        return friends.contains(userProfile);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DefaultUserProfile that = (DefaultUserProfile) object;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
