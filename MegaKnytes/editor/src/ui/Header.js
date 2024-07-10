import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import BatteryIndicator from "./BatteryIndicator";
import ConnectionIndicator from "./ConnectionIndicator";

export default function Header() {
  return (
    <AppBar
      position="fixed"
      sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}
    >
      <Toolbar>
        <Typography variant="h6" noWrap component="div">
          DTP Editor
        </Typography>
        <BatteryIndicator />
        <ConnectionIndicator connected={true} />
      </Toolbar>
    </AppBar>
  );
}
