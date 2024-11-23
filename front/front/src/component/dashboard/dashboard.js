import React, { useEffect, useState } from "react";
import typography from "../../assets/theme/base/typography";
import HorizontalBarChart from "../Chart/HorizontalChart";

const Dashboard = ({role}) => {
    /*const [userInfo, setUserInfo] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/v1/user/userinfo'); // Modifie l'URL selon ton API
                if (!response.ok) {
                    throw new Error('Erreur lors de la récupération des informations utilisateur');
                }
                const data = await response.json();
                setUserInfo(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUserInfo(); // Appelle la fonction lors du montage du composant
    }, []); // Le tableau vide signifie que l'effet ne s'exécute qu'une fois au montage

    if (loading) {
        return <div>Chargement des informations...</div>;
    }

    if (error) {
        return <div>Erreur : {error}</div>;
    }*/

        function configs(labels, datasets) {
            return {
              data: {
                labels,
                datasets: [...datasets],
              },
              options: {
                indexAxis: "y",
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                  legend: {
                    display: false,
                  },
                },
                scales: {
                  y: {
                    grid: {
                      drawBorder: false,
                      display: true,
                      drawOnChartArea: true,
                      drawTicks: false,
                      borderDash: [5, 5],
                      color: "#c1c4ce5c",
                    },
                    ticks: {
                      display: true,
                      color: "#b2b9bf",
                      padding: 10,
                      font: {
                        size: 11,
                        family: typography.fontFamily,
                        style: "normal",
                        lineHeight: 2,
                      },
                    },
                  },
                  x: {
                    grid: {
                      drawBorder: false,
                      display: false,
                      drawOnChartArea: true,
                      drawTicks: true,
                      color: "#c1c4ce5c",
                    },
                    ticks: {
                      display: true,
                      color: "#b2b9bf",
                      padding: 20,
                      font: {
                        size: 11,
                        family: typography.fontFamily,
                        style: "normal",
                        lineHeight: 2,
                      },
                    },
                  },
                },
              },
            };
          }
          

    return (
        <div>
            <h1>DashBoard Component</h1>
            {(
                <div>
                    <p>Role: {role}</p>
                <HorizontalBarChart></HorizontalBarChart>
                   
                </div>
                   )}
                </div>
            )
};

export default Dashboard;
