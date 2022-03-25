import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Login from '../pages/Login';
import Certificate from '../pages/Certificate'

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/gift-certificate-app/login" element={<Login/>}/>
                <Route path="/gift-certificate-app/*" element={<Certificate/>}/>
            </Routes>
        </Router>
    )
};
