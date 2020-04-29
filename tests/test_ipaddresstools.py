import pytest
import sys
import os
import re
base_path = os.path.join(os.path.abspath(os.path.dirname(__name__)))
sys.path.append(os.path.join(base_path, 'ipaddresstools'))
from ipaddresstools import ucast_ip, ucast_ip_mask, mcast_ip, mcast_ip_mask, cidr_check, \
                           get_neighbor_ip, whole_subnet_maker, number_check, ip, subnet_range, \
                           all_subnets_possible, all_subnets_longer_prefix, all_subnets_shorter_prefix, \
                           all_ip_address_in_subnet, random_cidr_mask, random_ucast_ip, random_mcast_ip, \
                           random_ucast_ip_mask, random_mcast_ip_mask


mask_conversion_test_data = [
    ("0.0.0.0", "255.255.255.255", "0"),
    ("128.0.0.0", "127.255.255.255", "1"),
    ("192.0.0.0", "63.255.255.255", "2"),
    ("224.0.0.0", "31.255.255.255", "3"),
    ("240.0.0.0", "15.255.255.255", "4"),
    ("248.0.0.0", "7.255.255.255", "5"),
    ("252.0.0.0", "3.255.255.255", "6"),
    ("254.0.0.0", "1.255.255.255", "7"),
    ("255.0.0.0", "0.255.255.255", "8"),
    ("255.128.0.0", "0.127.255.255", "9"),
    ("255.192.0.0", "0.63.255.255", "10"),
    ("255.224.0.0", "0.31.255.255", "11"),
    ("255.240.0.0", "0.15.255.255", "12"),
    ("255.248.0.0", "0.7.255.255", "13"),
    ("255.252.0.0", "0.3.255.255",  "14"),
    ("255.254.0.0", "0.1.255.255", "15"),
    ("255.255.0.0", "0.0.255.255",  "16"),
    ("255.255.128.0", "0.0.127.255", "17"),
    ("255.255.192.0", "0.0.63.255", "18"),
    ("255.255.224.0", "0.0.31.255", "19"),
    ("255.255.240.0", "0.0.15.255",  "20"),
    ("255.255.248.0", "0.0.7.255", "21"),
    ("255.255.252.0", "0.0.3.255", "22"),
    ("255.255.254.0", "0.0.1.255",  "23"),
    ("255.255.255.0", "0.0.0.255", "24"),
    ("255.255.255.128", "0.0.0.127", "25"),
    ("255.255.255.192", "0.0.0.63", "26"),
    ("255.255.255.224", "0.0.0.31", "27"),
    ("255.255.255.240", "0.0.0.15", "28"),
    ("255.255.255.248", "0.0.0.7", "29"),
    ("255.255.255.252", "0.0.0.3", "30"),
    ("255.255.255.254", "0.0.0.1", "31"),
    ("255.255.255.255", "0.0.0.0", "32"),
]


def test_ucast_ip_good():
    assert ucast_ip('192.168.1.1', return_tuple=False) is True


def test_ucast_ip_good_tuple():
    assert ucast_ip('192.168.1.1', return_tuple=True) == '192.168.1.1'


def test_ucast_ip_bad():
    assert ucast_ip('224.1.1.1', return_tuple=False) is False


def test_ucast_ip_mask_good():
    assert ucast_ip_mask('192.168.1.1/24', return_tuple=False) is True


def test_ucast_ip_mask_good_tuple():
    assert ucast_ip_mask('192.168.1.1/24', return_tuple=True) == ('192.168.1.1', '24')


def test_ucast_ip_mask_bad():
    assert ucast_ip_mask('224.1.1.1/24', return_tuple=False) is False


def test_mcast_ip_good():
    assert mcast_ip('224.1.1.1', return_tuple=False) is True


def test_mcast_ip_good_tuple():
    assert mcast_ip('224.1.1.1', return_tuple=True) == '224.1.1.1'


def test_mcast_ip_bad():
    assert mcast_ip('192.168.1.1', return_tuple=False) is False


def test_mcast_ip_mask_good():
    assert mcast_ip_mask('224.1.1.1/24', return_tuple=False) is True


def test_mcast_ip_mask_good_tuple():
    assert mcast_ip_mask('224.1.1.1/24', return_tuple=True) == ('224.1.1.1', '24')


def test_mcast_ip_mask_bad():
    assert mcast_ip_mask('192.168.1.1/24', return_tuple=False) is False


@pytest.mark.parametrize("standard,inverse,cidr", mask_conversion_test_data)
def test_cidr_value_good(standard, inverse, cidr):
    assert cidr_check(cidr, return_cidr=False) is True


@pytest.mark.parametrize("standard,inverse,cidr", mask_conversion_test_data)
def test_cidr_value_good_return_cidr(standard, inverse, cidr):
    assert cidr_check(cidr, return_cidr=True) == cidr


def test_cidr_value_bad():
    assert cidr_check('50', return_cidr=False) is False


def test_get_neighbor_ip_30():
    assert ('192.168.1.1', '192.168.1.2') == get_neighbor_ip('192.168.1.1')
    assert ('192.168.1.2', '192.168.1.1') == get_neighbor_ip('192.168.1.2')


def test_get_neighbor_ip_31():
    assert ('192.168.1.0', '192.168.1.1') == get_neighbor_ip('192.168.1.0', cidr='31')
    assert ('192.168.1.1', '192.168.1.0') == get_neighbor_ip('192.168.1.1', cidr='31')


def test_whole_subnet_maker_good():
    assert '192.168.1.0' == whole_subnet_maker('192.168.1.25', 24)


def test_number_check_good():
    assert number_check('5', return_number=False) is True


def test_number_check_bad():
    assert number_check('a', return_number=False) is False


def test_ip_good():
    assert ip('192.168.1.1', return_tuple=False) is True
    assert ip('224.1.1.1', return_tuple=False) is True


def test_ip_good_tuple():
    assert ip('192.168.1.1', return_tuple=True) == '192.168.1.1'
    assert ip('224.1.1.1', return_tuple=True) == '224.1.1.1'


def test_ip_bad():
    assert ip('192.168.1.1000', return_tuple=False) is False


def test_subnet_range_24():
    assert subnet_range('192.168.1.1', 24) == {'RANGE': '192.168.1.1 to 192.168.1.254',
                                               'CIDRVAL': '24',
                                               'NET': '192.168.1.0',
                                               'CIDR': '192.168.1.0/24',
                                               'BCAST': '192.168.1.255',
                                               'MASK': '255.255.255.0',
                                               'IP': '192.168.1.1',
                                               'INVMASK': '0.0.0.255'}


def test_subnet_range_16():
    assert subnet_range('192.168.1.1', 16) == {'RANGE': '192.168.0.1 to 192.168.255.254',
                                               'CIDRVAL': '16',
                                               'NET': '192.168.0.0',
                                               'CIDR': '192.168.0.0/16',
                                               'BCAST': '192.168.255.255',
                                               'MASK': '255.255.0.0',
                                               'IP': '192.168.1.1',
                                               'INVMASK': '0.0.255.255'}


def test_subnet_range_8():
    assert subnet_range('192.168.1.1', 8) == {'RANGE': '192.0.0.1 to 192.255.255.254',
                                               'CIDRVAL': '8',
                                               'NET': '192.0.0.0',
                                               'CIDR': '192.0.0.0/8',
                                               'BCAST': '192.255.255.255',
                                               'MASK': '255.0.0.0',
                                               'IP': '192.168.1.1',
                                               'INVMASK': '0.255.255.255'}


def test_subnet_range_1():
    assert subnet_range('192.168.1.1', 1) == {'RANGE': '128.0.0.1 to 255.255.255.254',
                                               'CIDRVAL': '1',
                                               'NET': '128.0.0.0',
                                               'CIDR': '128.0.0.0/1',
                                               'BCAST': '255.255.255.255',
                                               'MASK': '128.0.0.0',
                                               'IP': '192.168.1.1',
                                               'INVMASK': '127.255.255.255'}


def test_all_subnets_possible():
    assert all_subnets_possible('192.168.1.1', 26) == ['192.168.1.0/26', '192.168.1.0/27', '192.168.1.0/28',
                                                       '192.168.1.0/29', '192.168.1.0/30', '192.168.1.0/31',
                                                       '192.168.1.1/32']


def test_all_subnets_longer_prefix():
    assert all_subnets_longer_prefix('192.168.1.1', 26) == ['192.168.1.0/26', '192.168.1.0/27', '192.168.1.0/28',
                                                            '192.168.1.0/29', '192.168.1.0/30', '192.168.1.0/31',
                                                            '192.168.1.1/32']


def test_all_subnets_shorter_prefix():
    assert all_subnets_shorter_prefix('192.168.1.1', 26) == ['192.168.1.0/26', '192.168.1.0/25', '192.168.1.0/24',
                                                             '192.168.0.0/23', '192.168.0.0/22', '192.168.0.0/21',
                                                             '192.168.0.0/20', '192.168.0.0/19', '192.168.0.0/18',
                                                             '192.168.0.0/17', '192.168.0.0/16', '192.168.0.0/15',
                                                             '192.168.0.0/14', '192.168.0.0/13', '192.160.0.0/12',
                                                             '192.160.0.0/11', '192.128.0.0/10', '192.128.0.0/9',
                                                             '192.0.0.0/8', '192.0.0.0/7', '192.0.0.0/6', '192.0.0.0/5',
                                                             '192.0.0.0/4', '192.0.0.0/3', '192.0.0.0/2', '128.0.0.0/1']


def test_all_ip_address_in_subnet():
    assert all_ip_address_in_subnet('192.168.1.1', 28) == ['192.168.1.0', '192.168.1.1', '192.168.1.2', '192.168.1.3',
                                                           '192.168.1.4', '192.168.1.5', '192.168.1.6', '192.168.1.7',
                                                           '192.168.1.8', '192.168.1.9', '192.168.1.10', '192.168.1.11',
                                                           '192.168.1.12', '192.168.1.13', '192.168.1.14',
                                                           '192.168.1.15']


def test_random_cidr_mask():
    assert int(random_cidr_mask()) in range(16, 33)


def test_random_cidr_mask_bad():
    with pytest.raises(ValueError):
        random_cidr_mask(35)


def test_random_ucast_ip():
    regex_ucast_ip = re.compile(r'^((22[0-3])|(2[0-1][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|'
                                r'(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|'
                                r'(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|'
                                r'([1-9]?[0-9]))$')

    assert re.match(regex_ucast_ip, random_ucast_ip())


def test_random_mcast_ip():
    regex_mcast_ip = re.compile(r'^(((2[2-3][4-9])|(23[0-3]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]'
                                r'))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4]'
                                r'[0-9])|(1[0-9][0-9])|([1-9]?[0-9])))$')

    assert re.match(regex_mcast_ip, random_mcast_ip())


def test_random_ucast_ip_mask():
    regex_ucast_ip_and_mask = re.compile(r'^((22[0-3])|(2[0-1][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|'
                                         r'(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|'
                                         r'(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|'
                                         r'([1-9]?[0-9]))/((3[0-2])|([1-2]?[0-9]))$')

    assert re.match(regex_ucast_ip_and_mask, random_ucast_ip_mask())


def test_random_mcast_ip_mask():
    regex_mcast_ip_and_mask = re.compile(r'^(((2[2-3][4-9])|(23[0-3]))\.((25[0-5])|(2[0-4][0-9])|'
                                         r'(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|'
                                         r'([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|'
                                         r'([1-9]?[0-9]))/((3[0-2])|([1-2][0-9])|[3-9]))$')

    assert re.match(regex_mcast_ip_and_mask, random_mcast_ip_mask())
