import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Pagination from 'react-bootstrap/Pagination';
import UpdateNews from './updatetintuc';
import ModalAddNews from './insertnews';
import ModalDelete from './deletenews';

const TintucStaff = () => {
    const [News, setNews] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 10;
    const [showAdd, setShowAdd] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [showDelete, setShowDelete] = useState(false);
    const [editNewsData, setEditNewsData] = useState({});
    const [idToUpdate, setIdToUpdate] = useState(null);
    const [idToDelete, setIdToDelete] = useState(null);
    const handleCloseAdd = () => setShowAdd(false);
    const handleShowAdd = () => setShowAdd(true);

    const handleCloseEdit = () => {
        setShowEdit(false);
        setIdToUpdate(null);
    };

    const handleShowEdit = (data) => {
        setEditNewsData(data);
        setIdToUpdate(data.id);
        setShowEdit(true);
    };
    const handleShowDelete = (data) => {
        setEditNewsData(data);
        setIdToDelete(data.id);
        setShowDelete(true);
    };
    const handleUpdate = () => {
        getAllNews();
        handleCloseAdd();
        handleCloseEdit();
    };
   

    const handleDelete = () => {
        getAllNews();
        handleCloseAdd();
        handleCloseEdit();
        handleCloseDelete();
    };

    const handleCloseDelete = () => {
        setShowDelete(false);
        setIdToDelete(null);
    };

    const getAllNews = async () => {
        const res = await axios.get('https://652b64fe4791d884f1fdc2d3.mockapi.io/swp/news');
        if (res && res.data) {
            setNews(res.data);
        }
    };

    useEffect(() => {
        getAllNews();
    }, []);

    const maxPage = Math.ceil(News.length / itemsPerPage);

    const paginate = (pageNumber) => {
        if (pageNumber >= 1 && pageNumber <= maxPage) {
            setCurrentPage(pageNumber);
        }
    };

    return (
        <div style={{ marginTop: '100px' }}>
            <Button variant="primary" onClick={handleShowAdd}>
                Add News
            </Button>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Short Info</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {News.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage).map((data, index) => (
                        <tr key={index}>
                            <td>{data.id}</td>
                            <td>{data.name}</td>
                            <td>{data.shortinfo}</td>
                            <td>{data.date}</td>
                            <td>
                                <Button onClick={() => handleShowEdit(data)}>Edit</Button>
                                <Button onClick={() => handleShowDelete(data)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <Pagination>
                <Pagination.Prev
                    onClick={() => paginate(currentPage - 1)}
                    disabled={currentPage === 1}
                />
                {Array.from({ length: maxPage }, (_, i) => (
                    <Pagination.Item
                        key={i}
                        onClick={() => paginate(i + 1)}
                        active={currentPage === i + 1}
                    >
                        {i + 1}
                    </Pagination.Item>
                ))}
                <Pagination.Next
                    onClick={() => paginate(currentPage + 1)}
                    disabled={currentPage === maxPage}
                />
            </Pagination>

            <ModalAddNews
                show={showAdd}
                handleClose={handleCloseAdd}
                handleUpdate={handleUpdate}
            />

            <UpdateNews
                show={showEdit}
                handleClose={handleCloseEdit}
                idToUpdate={idToUpdate}
                newsData={editNewsData}
                handleUpdate={handleUpdate}
            />
            <ModalDelete
                show={showDelete}
                handleClose={handleCloseDelete}
                idToDelete={idToDelete}
                handleDelete={handleDelete}
            />
        </div>
    );
};

export default TintucStaff;