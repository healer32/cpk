/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 10.1.21-MariaDB : Database - stock
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stock` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `stock`;

/*Table structure for table `barang` */

DROP TABLE IF EXISTS `barang`;

CREATE TABLE `barang` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_kategori` int(11) NOT NULL,
  `id_toko` int(11) NOT NULL,
  `kode` varchar(20) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `harga` varchar(20) NOT NULL,
  `stok` int(11) NOT NULL,
  `harga_jual` varchar(20) NOT NULL,
  `diskon` float NOT NULL,
  `pajak` decimal(10,0) NOT NULL,
  `satuan` varchar(10) NOT NULL,
  `image` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=latin1;

/*Data for the table `barang` */

insert  into `barang`(`id`,`id_kategori`,`id_toko`,`kode`,`nama`,`harga`,`stok`,`harga_jual`,`diskon`,`pajak`,`satuan`,`image`) values 
(117,91,69,'001','b1','1',5,'2',0,0,'kg','http://192.168.43.15/stock/uploads/20191108110255FB_IMG_1573199986757.jpg'),
(118,91,69,'002','b2','11',35,'200',0,0,'kg','http://192.168.43.15/stock/uploads/20191108111954FB_IMG_1573199983345.jpg'),
(119,93,1,'001','b1','10',96,'20',0,0,'kg',''),
(120,92,69,'003','b3','10',25,'200',0,0,'kg','http://192.168.43.15/stock/uploads/20191125071905FB_IMG_1574391430492.jpg');

/*Table structure for table `kategori` */

DROP TABLE IF EXISTS `kategori`;

CREATE TABLE `kategori` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_toko` int(11) NOT NULL,
  `nama` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=latin1;

/*Data for the table `kategori` */

insert  into `kategori`(`id`,`id_toko`,`nama`) values 
(91,69,'k1'),
(92,69,'k2'),
(93,1,'a');

/*Table structure for table `list_transaksi` */

DROP TABLE IF EXISTS `list_transaksi`;

CREATE TABLE `list_transaksi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_toko` int(11) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `harga_jual` varchar(25) NOT NULL,
  `harga_dasar` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=801 DEFAULT CHARSET=latin1;

/*Data for the table `list_transaksi` */

insert  into `list_transaksi`(`id`,`id_toko`,`id_transaksi`,`id_barang`,`jumlah`,`harga_jual`,`harga_dasar`) values 
(784,69,484,118,1,'200','1'),
(783,69,483,118,1,'200','1'),
(782,69,483,117,1,'2','1'),
(781,69,482,118,2,'','1'),
(780,69,481,117,2,'2','1'),
(779,69,481,118,2,'200','1'),
(778,69,480,118,1,'200','1'),
(777,69,480,117,1,'2','1'),
(776,69,479,118,1,'200','1'),
(775,69,465,118,11,'200','1'),
(774,69,464,118,1,'','1'),
(773,69,463,117,1,'','1'),
(772,69,462,117,1,'','1'),
(771,69,461,117,2,'','1'),
(770,69,460,117,1,'','1'),
(769,69,459,117,1,'','1'),
(768,69,458,117,1,'','1'),
(767,69,457,117,1,'','1'),
(766,69,456,117,1,'','1'),
(765,69,455,117,1,'','1'),
(764,69,454,117,1,'','1'),
(785,69,485,118,1,'200','1'),
(786,69,486,120,10,'','100'),
(787,69,488,120,10,'','1'),
(788,69,489,120,5,'200','1'),
(789,69,490,120,2,'200','1'),
(790,69,491,120,2,'200','1'),
(791,69,492,120,1,'200','1'),
(792,69,493,117,1,'2','1'),
(793,69,493,118,1,'200','11'),
(794,69,493,120,1,'200','1'),
(795,69,494,117,1,'2','1'),
(796,69,494,118,1,'200','11'),
(797,69,494,120,1,'200','1'),
(798,69,495,120,2,'','10'),
(799,69,496,118,3,'200','11'),
(800,69,496,117,3,'2','1');

/*Table structure for table `toko` */

DROP TABLE IF EXISTS `toko`;

CREATE TABLE `toko` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(30) NOT NULL,
  `nama_pemilik` varchar(30) NOT NULL,
  `lokasi` text NOT NULL,
  `telephone` varchar(13) NOT NULL,
  `pajak` float NOT NULL,
  `diskon` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;

/*Data for the table `toko` */

insert  into `toko`(`id`,`nama`,`nama_pemilik`,`lokasi`,`telephone`,`pajak`,`diskon`) values 
(69,'Compu Parts','Chris','Komplek Kebon Jeruk','021555555',0,0);

/*Table structure for table `transaksi` */

DROP TABLE IF EXISTS `transaksi`;

CREATE TABLE `transaksi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_toko` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `modal` varchar(25) NOT NULL,
  `jual` varchar(25) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `tipe` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=497 DEFAULT CHARSET=latin1;

/*Data for the table `transaksi` */

insert  into `transaksi`(`id`,`id_toko`,`tanggal`,`modal`,`jual`,`jumlah`,`tipe`) values 
(454,69,'2019-11-08','1','0',1,0),
(455,69,'2019-11-08','1','0',1,0),
(456,69,'2019-11-08','1','0',1,0),
(457,69,'2019-11-08','1','0',1,0),
(458,69,'2019-11-08','1','0',1,0),
(459,69,'2019-11-08','1','0',1,0),
(460,69,'2019-11-08','1','0',1,0),
(461,69,'2019-11-08','2','0',2,0),
(462,69,'2019-11-08','1','0',1,0),
(463,69,'2019-11-08','1','0',1,0),
(464,69,'2019-11-08','1','0',1,0),
(465,69,'2019-11-08','11','2200',11,1),
(466,69,'2019-11-17','11','1624',11,1),
(467,69,'2019-11-17','11','1624',11,1),
(468,69,'2019-11-17','11','1624',11,1),
(469,69,'2019-11-17','11','1624',11,1),
(470,69,'2019-11-17','1','180',1,1),
(471,69,'2019-11-17','2','360',2,1),
(472,69,'2019-11-17','2','360',2,1),
(473,69,'2019-11-17','3','600',3,1),
(474,69,'2019-11-17','1','2',1,1),
(475,69,'2019-11-17','0','0',0,0),
(476,69,'2019-11-17','1','200',1,1),
(477,1,'2019-11-17','20','40',2,1),
(478,1,'2019-11-17','20','40',2,1),
(479,69,'2019-11-17','1','200',1,1),
(480,69,'2019-11-17','2','202',2,1),
(481,69,'2019-11-17','4','404',4,1),
(482,69,'2019-11-25','2','0',2,0),
(483,69,'2019-11-25','2','202',2,1),
(484,69,'2019-11-25','1','200',1,1),
(485,69,'2019-11-25','1','200',1,1),
(486,69,'2019-11-25','1000','0',10,0),
(487,69,'2019-11-25','12','0',2,0),
(488,69,'2019-11-25','10','0',10,0),
(489,69,'2019-11-25','5','1000',5,1),
(490,69,'2019-12-08','2','400',2,1),
(491,69,'2019-12-08','2','400',2,1),
(492,69,'2019-12-08','1','200',1,1),
(493,69,'2019-12-08','13','402',3,1),
(494,69,'2019-12-08','13','402',3,1),
(495,69,'2019-12-08','20','0',2,0),
(496,69,'2019-12-08','36','606',6,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_toko` int(11) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `telephone` varchar(13) NOT NULL,
  `alamat` text NOT NULL,
  `password` varchar(20) NOT NULL,
  `tipe` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`id_toko`,`nama`,`email`,`telephone`,`alamat`,`password`,`tipe`) values 
(80,69,'Dion','dion.k3.wijaya@gmail.com','082298459834','Jalan Kebon Jeruk','test',0),
(81,69,'Staff 1','staff@cp.com','081212123434','test','test',2),
(82,69,'Staff 2','a@a.a','088','test','test',2),
(83,69,'a','a','1','a','a',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
