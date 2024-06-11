import React from "react";
import "../styles/Header.css";
import ConnectionIndicator from "./ConnectionIndicator";
import BatteryIndicator from "./BatteryIndicator";

export default function Header() {
  return (
    <header className="header">
      <h1>DTP Web Editor</h1>
      <BatteryIndicator />
      <ConnectionIndicator connected={true} />
    </header>
  );
}
