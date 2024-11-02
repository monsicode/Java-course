package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;


public class DefaultUserProfile implements UserProfile {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private final int id;
    private final String username;
    Set<Interest> interestsUser;
    Set<UserProfile> friends;

    //later for the bigger class
    //private final Map<UserProfile, List<UserProfile>> friends = null;

    public DefaultUserProfile(String username) {
        this.id = counter.incrementAndGet();
        this.username = username;
    }

    public int getUserId(){
         return id;
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
            return friends.add(userProfile);
        }

    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null ) {
            throw new IllegalArgumentException("The user is trying to add themselves as a friend or the user is null!");
        }

        if (!friends.contains(userProfile)) {
            return false;
        } else {
            return friends.remove(userProfile);
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
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
