import React from "react";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import Login from "./Login";
import RegisterForm from "./Register";
import SearchPage from "./Search";
import AlbumPage from "./Album";



const App: React.FC = () => {
  const token = localStorage.getItem("token");

  return (
    <BrowserRouter>
      <div id="app-main-div">
        <div id="app-content-div">
          <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<RegisterForm />} />
            <Route
              path="/search"
              element={token ? <SearchPage /> : <Navigate to="/login" replace />}
            />
            <Route
              path="/album"
              element={token ? <AlbumPage /> : <Navigate to="/login" replace />}
            />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
