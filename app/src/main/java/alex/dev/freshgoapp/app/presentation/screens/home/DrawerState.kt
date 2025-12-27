package alex.dev.freshgoapp.app.presentation.screens.home

enum class DrawerState {
    Opened,
    Closed
}

fun DrawerState.isOpened(): Boolean {
    return this == DrawerState.Opened
}

fun DrawerState.reverse(): DrawerState {
    return if (this == DrawerState.Opened) DrawerState.Closed else DrawerState.Opened
}