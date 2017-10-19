package cz.devconf2017;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import cz.devconf2017.floorplan.FloorPlanFragment;
import cz.devconf2017.offline.AboutFragment;
import cz.devconf2017.offline.SocialFragment;
import cz.devconf2017.offline.VenueFragment;
import cz.devconf2017.session.SessionsFragment;
import cz.devconf2017.speaker.SpeakersFragment;
import cz.devconf2017.voting.VotingResultFragment;

public class MainNavigationHelper {

    private final FragmentManager supportFragmentManager;

    private Section currentSection = Section.HOME;

    public MainNavigationHelper(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    public void navigate(Section section) {
        if (section == currentSection) {
            return;
        }

        currentSection = section;

        Fragment fragment = getFragment(section);

        replaceFragment(fragment);
    }

    private Fragment getFragment(Section section) {
        Fragment fragment;

        switch (section) {
            case HOME:
//                fragment = HomeFragment.newInstance();
                fragment = null;
                break;

            case DAY_1:
                fragment = SessionsFragment.newInstance(1);
                break;

            case DAY_2:
                fragment = SessionsFragment.newInstance(2);
                break;

            case DAY_3:
                fragment = SessionsFragment.newInstance(3);
                break;

            case FAVORITES:
//                fragment = FavoritesFragment.newInstance();
                fragment = null;
                break;

            case VOTING:
                fragment = VotingResultFragment.newInstance(0);
                break;

            case VENUE:
                fragment = VenueFragment.newInstance();
                break;

            case FLOOR_PLAN:
                fragment = FloorPlanFragment.newInstance();
                break;

            case SPEAKERS:
                fragment = SpeakersFragment.newInstance();
                break;

            case SOCIAL_EVENT:
                fragment = SocialFragment.newInstance();
                break;

            case ABOUT:
                fragment = AboutFragment.newInstance();
                break;

            default:
                throw new IllegalArgumentException("Section does not exist: " + section);
        }

        return fragment;
    }

    private void replaceFragment(Fragment fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .commit();
    }

    public boolean isCurrentSection(Section section) {
        return section == currentSection;
    }

    enum Section {
        HOME,
        DAY_1,
        DAY_2,
        DAY_3,
        VENUE,
        FLOOR_PLAN,
        SPEAKERS,
        SOCIAL_EVENT,
        ABOUT,
        VOTING,
        FAVORITES
    }
}