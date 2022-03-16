import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from './Login';
import Certificate from '../pages/Certificate'
import CertEdit from './CertEdit'


export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/gift-certificate-app/login" element={<Login />} />
                <Route path="/gift-certificate-app/certificates" element={<Certificate />} />
                <Route path='/gift-certificate-app/certificates/:id' element={<CertEdit />} />
                
                <Route path="/" element={<Navigate replace to='/gift-certificate-app/certificates' />} />
                <Route path="/gift-certificate-app" element={<Navigate replace to='/gift-certificate-app/certificates' />} />
            </Routes>
        </Router>
    )
};
