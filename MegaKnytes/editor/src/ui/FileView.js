import React from "react";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import "../styles/FileView.css";
import AddCircleOutlineRoundedIcon from "@mui/icons-material/AddCircleOutlineRounded";
import FileCard from "./FileCard";

export default function FileView() {
  return (
    <Box className="container">
      <Toolbar />
      <Box className="header-container">
        <Typography variant="h4" component="div">
          My Files
        </Typography>
        <Button
          variant="contained"
          color="primary"
          startIcon={<AddCircleOutlineRoundedIcon />}
          className="new-file-button"
          size="large"
        >
          New File
        </Button>
      </Box>
      <Box className="FileView">
        <Box className="card-container">
          {Array.from({ length: 16 }).map((_, index) => (
            <FileCard
              fileName="test"
              editDate="June 12 @ 11:32pm"
              countStates={index}
            />
          ))}
        </Box>
      </Box>
    </Box>
  );
}
