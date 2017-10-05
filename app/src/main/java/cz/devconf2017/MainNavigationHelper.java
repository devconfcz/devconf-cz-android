package cz.devconf2017;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import cz.devconf2017.offline.AboutFragment;
import cz.devconf2017.offline.SocialFragment;
import cz.devconf2017.offline.VenueFragment;

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
                fragment = new HomeFragment();
                break;

            case DAY_1:
                fragment = new TalkFragment();
                break;

            case DAY_2:
                fragment = new TalkFragment();
                break;

            case DAY_3:
                fragment = new TalkFragment();
                break;

            case FAVORITES:
                fragment = new FavoritesFragment();
                break;

            case VOTING:
                fragment = new VotingFragment();
                break;

            case VENUE:
                fragment = VenueFragment.newInstance();
                break;

            case FLOOR_PLAN:
                fragment = new FloorPlanFragment();
                break;

            case SPEAKERS:
                fragment = new SpeakersFragment();
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