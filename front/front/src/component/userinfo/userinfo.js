import { useEffect, useState } from "react";


const Userinfo = () => {
    
    const [userInfo, setUserInfo] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    useEffect(() => {
        const fetchUserInfo = async () => {
            const token = localStorage.getItem('authToken'); 
            console.log(token," io le token"); // Le token doit être sauvegardé correctement
            const response = await fetch('http://localhost:8080/api/v1/user/userinfo', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,  // S'assurer que le token est bien envoyé ici
                    'Content-Type': 'application/json',
                },
            });
            

            if (response.ok) {
                const data = await response.json();
                console.log(data);
                setUserInfo(data);
            } else {
                console.error('Erreur lors de la récupération des informations utilisateur');
            }
        };

        fetchUserInfo();
    }, []);

    return (
        <div>
            DashBoard Component
           <div>
           <p>nom: {userInfo.nom}</p>
           <p>prenom: {userInfo.prenom}</p>
           <p>email: {userInfo.email}</p>
           
           </div>

        </div>
    );}


export default Userinfo;
