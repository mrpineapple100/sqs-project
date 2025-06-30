// src/components/NavigationBar.tsx
import React from "react";
import { AppBar, Toolbar, Box, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const NavigationBar: React.FC = () => {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <Box sx={{ flexGrow: 1, width: "100%" }}>
      <AppBar
        position="static"
        sx={{
          width: "100%",
          background: "linear-gradient(90deg, #ffcb05 0%, #3b4cca 100%)",
          boxShadow: "0 2px 8px rgba(0,0,0,0.3)",
        }}
      >
        <Toolbar sx={{ justifyContent: "space-between" }}>
          <button
            onClick={() => navigate("/search")}
            style={{
              background: "none",
              border: "none",
              padding: 0,
              cursor: "pointer",
            }}
            aria-label="Zur Suche"
          >
            <img
              src="/uploads/logo.png"
              alt="Pokéball"
              style={{ height: 40, display: "block" }}
            />
          </button>
          <Box>
            <Button color="inherit" onClick={logout}>
              Logout
            </Button>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default NavigationBar;
