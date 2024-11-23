import { Route, Routes } from 'react-router-dom';
import './App.css';
import Header from './component/header/header';
import Dashboard from './component/dashboard/dashboard';
import NoMatch from './component/noMatch/NoMatch';
import PostUtilisateur from './component/utilisateur/PostUtilisateur';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './component/login/login';
import Userinfo from './component/userinfo/userinfo';
import Agent_home from './component/Agent/Agent_home';
import Chef_Service_home from './component/Chef_Service/Chef_Service_home';
import DPR_Home from './component/DPR/DPR_Home';
import ProtectedRoute from './component/header/route/protectedroute';



function App() {
  return (
    <div className="App">
     <Header></Header>

     <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="*" element={<NoMatch />} />
      <Route path="/utilisateur-post" element={<PostUtilisateur />} />
      <Route path="/Login" element={<Login />} />
      
      <Route path="/AGENT" element={<Agent_home />} />
      <Route path="/CHEF_SERVICE" element={<Chef_Service_home/>} />
      <Route path="/DPR_SAF" element={<DPR_Home/>} />

      <Route path="/Userinfo" element={<ProtectedRoute ><Userinfo /> </ProtectedRoute>} />
      <Route path="/AGENT" element={<Agent_home />} />
      <Route path="/CHEF_SERVICE" element={<Chef_Service_home/>} />
      <Route path="/DPR_SAF" element={<DPR_Home/>} />0
     </Routes>
    </div>
  );
}

export default App;
