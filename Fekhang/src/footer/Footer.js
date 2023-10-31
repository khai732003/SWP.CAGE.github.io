import React from 'react';
import '../footer/footer.scss';
import FooterAdmin from '../content/dashboard/components/FooterAdmin';
import { useLocation } from 'react-router-dom';


const Footer = (props) => {
    const location = useLocation();

    const isLoginPage = location.pathname === '/login';
    const isRegisterPage = location.pathname === '/register';
    const isSuccess = location.pathname === '/paysuccess';
    const isUserManager = location.pathname === '/usermanagement';
    const isAdmin = location.pathname === '/admin';
    const isAddUser = location.pathname === '/add-user';
    const isUpdateUser = /^\/update\/\d+$/.test(location.pathname);
    const isRevenue = location.pathname === "/revenue";
    const isProductManager = location.pathname === "/productmanagement";
    const isStaffManager = location.pathname === '/staffmanagement';
    const isUpdateProduct = /^\/update\/\d+$/.test(location.pathname);
    const isAddProduct = location.pathname === '/addproduct';
    

    const isPaypal= location.pathname === '/paypal';


    if (isLoginPage || isRegisterPage || isSuccess || isPaypal) {
        return null;
    }

    if (isUserManager || isAdmin || isAddUser || isUpdateUser || isRevenue || isProductManager || isAddProduct || isUpdateProduct || isStaffManager) {
        return <FooterAdmin />
    }
    return (
        <div className='Foot'>
            <footer className="container">
                <div className="row">
                    <div className="col-md-4" >
                        <span>THÔNG TIN LIÊN LẠC</span>
                        <p><strong>Địa chỉ: </strong> Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh 700000</p>
                        <p> <strong>Email: </strong>
                            <a href="mailto:hcmuni.fpt.edu.vn">hcmuni.fpt.edu.vn</a></p>
                        <p><strong>Phone:</strong> 19009477</p>
                    </div>
                    <div className="col-md-4">
                        <span>GIỜ BÁN HÀNG</span>
                        <p>Thứ Hai - Thứ Sáu: 09:00 AM - 09:00 PM</p>
                        <p>Thứ 7: 09:00 AM - 07:00 PM</p>
                        <p>Chủ nhật: Đóng cửa</p>
                    </div>
                    <div className="col-md-4">
                        <span>GIỜ DỊCH VỤ</span>
                        <p>Thứ Hai - Thứ Sáu: 09:00 AM - 09:00 PM</p>
                        <p>Thứ 7: 09:00 AM - 07:00 PM</p>
                        <p>Chủ nhật: Đóng cửa</p>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-4">
                        <span>NHẬN KHUYẾN MÃI</span>
                        <form>
                            <div className="form-group">
                                <div className="input-group">
                                    <input
                                        type="email"
                                        className="form-control"
                                        id="emailInput"
                                        placeholder="Enter your email"
                                    />
                                    <div className="input-group-append">
                                        <button type="submit" className="btn btn-primary">
                                            ĐĂNG KÝ
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div className="col-md-4">
                        <span>MẠNG XÃ HỘI</span>
                        <div className='social-icons'>
                            <a
                                href="https://www.facebook.com/FPTU.HCM"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                <i class="bi bi-facebook"></i>
                            </a>
                            <a
                                href="https://www.youtube.com/@FPTUniversityHCM"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                <i class="bi bi-youtube"></i>
                            </a>
                        </div>
                    </div>
                    <div className="col-md-4">
                        <iframe
                            title="Google Map"
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.609941530492!2d106.80730807486965!3d10.84113285799728!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752731176b07b1%3A0xb752b24b379bae5e!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgVFAuIEhDTQ!5e0!3m2!1svi!2s!4v1695706352214!5m2!1svi!2s"
                            width="400"
                            height="200"
                            style={{ border: '0' }}
                            allowFullScreen=""
                            aria-hidden="false"
                            tabIndex="0"
                        ></iframe>
                    </div>
                </div>
            </footer>
        </div>
    )
};

export default Footer;
