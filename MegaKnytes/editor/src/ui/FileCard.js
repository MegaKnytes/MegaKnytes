import React from "react";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import "../styles/FileView.css";
import { Button } from "@mui/material";

const cardPaperStyle = {
  backgroundColor: "grey",
  color: "white",
  whiteSpace: "nowrap",
  overflow: "hidden",
  textOverflow: "ellipsis",
};

export default function FileCard(props) {
  return (
    <Card className="card" sx={cardPaperStyle}>
      <CardContent>
        <Typography variant="h6" component="div">
          {props.fileName}
        </Typography>
        <Typography variant="h8" component="div">
          Last Edited: {props.editDate} | {props.countStates} states
        </Typography>
      </CardContent>
    </Card>
  );
}
