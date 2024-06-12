import React from "react";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import "../styles/FileView.css";
import AddCircleOutlineRoundedIcon from "@mui/icons-material/AddCircleOutlineRounded";

const fileCard = (
  <React.Fragment>
    <CardContent>
      <Typography variant="h6" component="div">
        {"[File Name]"}
      </Typography>
      <Typography variant="h8" component="div">
        Last Edited: June 6th @ 11:13pm | # states
      </Typography>
    </CardContent>
  </React.Fragment>
);

const cardPaperStyle = {
  backgroundColor: "grey",
  color: "white",
  whiteSpace: "nowrap",
  overflow: "hidden",
  textOverflow: "ellipsis",
};

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
          <Card className="card" sx={cardPaperStyle}>
            {fileCard}
          </Card>
          <Card className="card" sx={cardPaperStyle}>
            {fileCard}
          </Card>
          <Card className="card" sx={cardPaperStyle}>
            {fileCard}
          </Card>
          <Card className="card" sx={cardPaperStyle}>
            {fileCard}
          </Card>
          <Card className="card" sx={cardPaperStyle}>
            {fileCard}
          </Card>
        </Box>
      </Box>
    </Box>
  );
}
