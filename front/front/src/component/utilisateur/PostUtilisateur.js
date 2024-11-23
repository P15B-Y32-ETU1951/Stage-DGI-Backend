import React, { useState } from 'react';
import { Button, Form, Container, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const PostUtilisateur = () => {
    const [utilisateur, setUtilisateur] = useState({
        nom: "",
        email: "",
        password: "",
        prenom: "",
        service:"",
        role:"AGENT"
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setUtilisateur({
            ...utilisateur,
            [name]: value
        });
    };

    const navigate = useNavigate();
    const handleSubmit = async(event) => {
        event.preventDefault();
        console.log(utilisateur);

try {
    const response = await fetch('http://localhost:8080/api/v1/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(utilisateur)
    });
    const data=await response.json();
    console.log("utilisateur created", data);
    navigate("/login");
} catch (error) {
    console.error("error saving utilisateur:",error.message);
    console.log("error saving utilisateur:",error.response);
}
    };
    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md={6}>
                    <h1 className="text-center my-4">Sign Up</h1>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Nom</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Entrer nom" 
                                name="nom"
                                value={utilisateur.nom} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Prenom</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Entrer prenom" 
                                name="prenom"
                                value={utilisateur.prenom} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control 
                                type="email" 
                                placeholder="Entrer email" 
                                name="email"
                                value={utilisateur.email} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control 
                                type="password" 
                                placeholder="Entrer mot de passe" 
                                name="password"
                                value={utilisateur.password} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicSelect">
                            <Form.Label>Role</Form.Label>
                            <select className="form-select" aria-label="Default select example" name="role" value={utilisateur.role} onChange={handleInputChange}>
                                <option value={"CHEF_SERVICE"}>CHEF de SERVICE</option>
                                <option value={"AGENT"}>AGENT</option>
                            </select>
                        </Form.Group>

                        <Button variant="primary" type="submit" className="w-100">
                            Submit
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default PostUtilisateur;
