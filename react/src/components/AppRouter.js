import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import Login from '../pages/Login';
import Certificate from '../pages/Certificate'

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/gift-certificate-app/login" element={<Login/>}/>
                <Route path="/gift-certificate-app/certificates" element={<Certificate/>}/>
                <Route path='/gift-certificate-app/certificates/*' element={<Certificate/>}/>

                <Route path="/" element={<Navigate replace to='/gift-certificate-app/certificates'/>}/>
                <Route path="/gift-certificate-app"
                       element={<Navigate replace to='/gift-certificate-app/certificates'/>}/>
            </Routes>
        </Router>
    )
};
