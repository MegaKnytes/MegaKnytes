import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import SourceRoundedIcon from "@mui/icons-material/SourceRounded";
import FavoriteRoundedIcon from "@mui/icons-material/FavoriteRounded";

const drawerWidth = 240;

export default function SideNav() {
  return (
    <Drawer
      variant="permanent"
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        [`& .MuiDrawer-paper`]: {
          width: drawerWidth,
          boxSizing: "border-box",
        },
      }}
    >
      <Toolbar />
      <Box sx={{ overflow: "auto" }}>
        <List>
          <ListItem key="My Files">
            <ListItemButton selected={true}>
              <ListItemIcon>
                <SourceRoundedIcon />
              </ListItemIcon>
              <ListItemText primary="My Files" />
            </ListItemButton>
          </ListItem>
          <ListItem key="Favorites">
            <ListItemButton>
              <ListItemIcon>
                <FavoriteRoundedIcon />
              </ListItemIcon>
              <ListItemText primary="Favorites" />
            </ListItemButton>
          </ListItem>
        </List>
      </Box>
    </Drawer>
  );
}
