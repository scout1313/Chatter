package com.dral.Chatter;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;

public class ChatterPermissionsHandler {

    Chatter plugin;

    public ChatterPermissionsHandler(Chatter plugin) {
        this.plugin = plugin;
    }
    
    /*
     * Info :D
     */
    @SuppressWarnings("deprecation")
	public String getInfo(Player player, String info) {
        String name = player.getName();
        String world = player.getWorld().getName();
        
        plugin.logIt("a info node is asked!! staring with search!");
    	
        if (plugin.permissionsB) { // Permissions things
            if (plugin.permissions3) { //permissions 3 :D
            	plugin.logIt("asking permissions3 for the node "+info+" from player "+name);
                String userString = plugin.permissions.getInfoString(world, name, info, false);
                String group = plugin.permissions.getPrimaryGroup(world, name);

                if (userString != null && !userString.isEmpty()) return userString;
                if (group == null) return "";

                String groupString = plugin.permissions.getInfoString(world, group, info, true);
                if (groupString == null) return "";
                return groupString;
            } else { // permissions other or so :/
            	plugin.logIt("asking SOMETHING ELSE for the node "+info+" from player "+name);
                String group = plugin.permissions.getGroup(world, name);
                String userString = plugin.permissions.getUserPermissionString(world, name, info);
                if (userString != null && !userString.isEmpty())
                    return userString;

                if (group == null)
                    return "";

                return plugin.permissions.getGroupPermissionString(world, group, info);
            }
        }
        
        if (plugin.gmPermissionsB) { // groupmanager :D
        	plugin.logIt("asking groupmanager for the node "+info+" from player "+name);
            String pName = player.getName();
            String group = plugin.gmPermissions.getGroup(pName);
            String userString = plugin.gmPermissions.getUserPermissionString(pName, info);

            if (userString != null && !userString.isEmpty()) return userString;
            if (group == null) return "";

            return plugin.gmPermissions.getGroupPermissionString(group, info);
        }
            
        if (plugin.PEXB) { // Pex shizzle //
        	plugin.logIt("asking Pex for the node "+info+" from player "+name);
    		PermissionUser pexPlayer = plugin.pexPermissions.getUser(player);
    		String option = "";
    		
    		if (info.equalsIgnoreCase("prefix")) {
    			option = pexPlayer.getPrefix();
    			if (!option.isEmpty()) return option;
    		} else if (info.equalsIgnoreCase("suffix")) {
    			option = pexPlayer.getSuffix();
    			if (!option.isEmpty()) return option;
    		}
    		
    		option = pexPlayer.getOwnOption(info, world);
    		if (!option.isEmpty()) return option;
    		option = pexPlayer.getOption(info, world);
    		if (!option.isEmpty()) return option;
    		option = pexPlayer.getOwnOption(info);
    		if (!option.isEmpty()) return option;
    		option = pexPlayer.getOption(info);
    		if (!option.isEmpty()) return option;
    		return "";
        }

        if (plugin.bPermB) { // bPermissions, fail :P
        	plugin.logIt("asking bPermissions for the node "+info+" from player "+name);
            String userString = plugin.bInfoR.getValue(player, info);
            if (userString != null && !userString.isEmpty()) return userString;

            return "";
        }

	return "";
    }

    @SuppressWarnings("deprecation")
	public String getGroup(Player player) {
        String name = player.getName();
        String world = player.getWorld().getName();
    	
        if (plugin.permissionsB) { // Permissions things
            if (plugin.permissions3) {
            	plugin.logIt("asking permissions3 for the group from player "+name);
                String group = plugin.permissions.getPrimaryGroup(world, name);
                if (group == null) return "";

                return group;
            } else {
            	plugin.logIt("asking SOMETHING ELSE for the group from player "+name);
                String group = plugin.permissions.getGroup(world, name);
                if (group == null) return "";

                return group;
            }
        }
        
        if (plugin.gmPermissionsB) { // groupmanager :D
        	plugin.logIt("asking groupmanager for the group from player "+name);
            String group = plugin.gmPermissions.getGroup(name);
            if (group == null) return "";

            return group;
        }
            
        if (plugin.PEXB) { // Pex shizzle //  
        	plugin.logIt("asking PeX for the group from player "+name);
            String group = plugin.pexPermissions.getUser(name).getGroupsNames(world)[0];
            if (group == null) return "";

            return group;
        }

        if (plugin.bPermB) { // bPermissions, fail :P
        	plugin.logIt("asking bPermissions for the group from player "+name);
            String group = plugin.bPermS.getPermissionSet(player.getWorld()).getGroups(player).get(0);
            if (group == null) return "";

            return group;
        }

	return "";
    }

    public Boolean checkPermissions(Player player, String node) {
        return checkPermissions(player, node, true);
    }

    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (plugin.permissionsB)
            if (plugin.permissions.has(player, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissions.has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        if (useOp)
            return player.isOp();

        return player.hasPermission(node);
    }
}