import unittest
from modIpAddressTools import ucast_ip, ucast_ip_mask, mcast_ip, mcast_ip_mask, cidr_check, get_neighbor_ip, \
    whole_subnet_maker, number_check


class TestIpAddressUnicast(unittest.TestCase):

    def test_ucast_ip(self):
        """ Testing for a good unicast ip response """
        self.assertTrue(ucast_ip('192.168.1.1', return_tuple=False))
        self.assertFalse(ucast_ip('224.1.1.1', return_tuple=False))

    def test_ucast_ip_mask(self):
        """ Testing for a good unicast ip and mask response """
        self.assertTrue(ucast_ip_mask('192.168.1.1/24', return_tuple=False))
        self.assertFalse(ucast_ip_mask('224.1.1.1/24', return_tuple=False))


class TestIpAddressMulticast(unittest.TestCase):

    def test_mcast_ip(self):
        """ Testing for a good mcast ip response """
        self.assertTrue(mcast_ip('224.1.1.1', return_tuple=False))
        self.assertFalse(mcast_ip('192.168.1.1', return_tuple=False))

    def test_mcast_ip_mask(self):
        """ Testing for a good mcast ip and mask response """
        self.assertTrue(mcast_ip_mask('224.1.1.1/24', return_tuple=False))
        self.assertFalse(mcast_ip_mask('192.168.1.1/24', return_tuple=False))


class TestMisc(unittest.TestCase):

    def test_cidr_check(self):
        """ Testing for good cidr"""
        self.assertTrue(cidr_check('24', return_cidr=False))
        self.assertFalse(cidr_check('50', return_cidr=False))

    def test_get_neighbor_ip_30(self):
        """ Testing good neighbor ip's with /30 """
        self.assertTupleEqual(('192.168.1.1', '192.168.1.2'), get_neighbor_ip('192.168.1.1'))
        self.assertTupleEqual(('192.168.1.2', '192.168.1.1'), get_neighbor_ip('192.168.1.2'))

    def test_get_neighbor_ip_31(self):
        """ Testing good neighbor ip's with /31 """
        self.assertTupleEqual(('192.168.1.1', '192.168.1.0'), get_neighbor_ip('192.168.1.1', cidr='31'))
        self.assertTupleEqual(('192.168.1.0', '192.168.1.1'), get_neighbor_ip('192.168.1.0', cidr='31'))

    def test_whole_subnet_maker_good(self):
        """ Testing good whole subnet returned """
        self.assertEqual('192.168.1.0', whole_subnet_maker('192.168.1.25', 24))

    def test_number_check(self):
        """ Testing for a good number """
        self.assertTrue(number_check('5', return_number=False))
        self.assertFalse(number_check('a', return_number=False))



if __name__ == '__main__':
    unittest.main()
