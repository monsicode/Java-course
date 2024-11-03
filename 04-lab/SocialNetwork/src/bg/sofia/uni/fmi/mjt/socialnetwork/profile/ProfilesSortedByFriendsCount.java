package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Comparator;

public class ProfilesSortedByFriendsCount implements Comparator<UserProfile> {

    @Override
    public int compare(UserProfile o1, UserProfile o2) {
        return Integer.compare(o2.getFriends().size(), o1.getFriends().size());
    }
}
