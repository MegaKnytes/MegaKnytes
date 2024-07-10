import * as React from "react";
import Box from "@mui/material/Box";
import Header from "./ui/Header";
import SideNav from "./ui/SideNav";
import FileView from "./ui/FileView";

export default function App() {
  return (
    <Box sx={{ display: "flex" }}>
      <Header />
      <SideNav />
      <FileView />
    </Box>
  );
}
