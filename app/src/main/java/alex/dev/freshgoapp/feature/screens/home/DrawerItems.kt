package alex.dev.freshgoapp.feature.screens.home

import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.R
import androidx.annotation.StringRes

enum class DrawerItems(@param:StringRes val titleRes: Int, val icon: Int) {
    Profile(
        titleRes = R.string.drawer_profile,
        icon = Resources.Icon.Person
    ),
    Locations(
        titleRes =R.string.drawer_locations,
        icon = Resources.Icon.MapPin
    ),
    Rewards(
        titleRes = R.string.drawer_rewards,
        icon = Resources.Icon.Heart
    ),
    Offers(
        titleRes = R.string.drawer_offers,
        icon = Resources.Icon.Gift
    ),
    ContactUs(
        titleRes = R.string.drawer_contacts,
        icon = Resources.Icon.Edit
    ),
    SignOut(
        titleRes = R.string.drawer_signout,
        icon = Resources.Icon.SignOut
    ),
    AdminPanel(
        titleRes = R.string.drawer_admin_panel,
        icon = Resources.Icon.Unlock
    ),
}