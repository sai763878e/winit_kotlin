package com.example.winit_kotlin.menu

import java.util.Vector

data class MenuDO(
    var menuName: String = "",
    var menuImage: Int = 0,
    var vecMenuDOs: Vector<MenuDO> = Vector(),
    var objMenu: MenuClass.MENUS? = null
)
