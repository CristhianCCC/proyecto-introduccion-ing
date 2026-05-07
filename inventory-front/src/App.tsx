import { Route, Routes } from "react-router-dom";
import Auth from "./components/Auth";
import Inventory from "./components/Inventory";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {

  return (
    <>
      <Routes>
        <Route path="/" element={<Auth />} />
        <Route path="/inventory" element={<ProtectedRoute> <Inventory /> </ProtectedRoute>} />
      </Routes>
    </>
  )
}

export default App;
