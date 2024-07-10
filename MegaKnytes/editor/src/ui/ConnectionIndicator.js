import React from "react";
import "../styles/ConnectionIndicator.css";

export default function ConnectionIndicator(props) {
  return (
    <div
      className={`connected-indicator ${
        props.connected ? "connected" : "disconnected"
      }`}
    >
      <div
        className={`dot ${props.connected ? "connected" : "disconnected"}`}
      ></div>
      <span>{props.connected ? "Connected | 00ms" : "Disconnected"}</span>
    </div>
  );
}
