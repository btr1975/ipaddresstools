from ipaddresstools.ipaddresstools import ucast_ip, ucast_ip_mask, mcast_ip, mcast_ip_mask, cidr_check, \
    get_neighbor_ip, whole_subnet_maker, number_check, ip, subnet_range, all_subnets_possible, \
    all_subnets_longer_prefix, all_subnets_shorter_prefix, all_ip_address_in_subnet


def test_ucast_ip_good():
    assert ucast_ip('192.168.1.1', return_tuple=False) == True


def test_ucast_ip_bad():
    assert ucast_ip('224.1.1.1', return_tuple=False) == False


def test_ucast_ip_mask_good():
    assert ucast_ip_mask('192.168.1.1/24', return_tuple=False) == True


def test_ucast_ip_mask_bad():
    assert ucast_ip_mask('224.1.1.1/24', return_tuple=False) == False


def test_mcast_ip_good():
    assert mcast_ip('224.1.1.1', return_tuple=False) == True


def test_mcast_ip_bad():
    assert mcast_ip('192.168.1.1', return_tuple=False) == False


def test_mcast_ip_mask_good():
    assert mcast_ip_mask('224.1.1.1/24', return_tuple=False) == True


def test_mcast_ip_mask_bad():
    assert mcast_ip_mask('192.168.1.1/24', return_tuple=False) == False


def test_cidr_value_good():
    assert cidr_check('24', return_cidr=False)== True


def test_cidr_value_bad():
    assert cidr_check('50', return_cidr=False) == False


def test_get_neighbor_ip_30():
    assert ('192.168.1.1', '192.168.1.2') == get_neighbor_ip('192.168.1.1')
    assert ('192.168.1.2', '192.168.1.1') == get_neighbor_ip('192.168.1.2')


def test_get_neighbor_ip_31():
    assert ('192.168.1.1', '192.168.1.2') == get_neighbor_ip('192.168.1.1')
    assert ('192.168.1.2', '192.168.1.1') == get_neighbor_ip('192.168.1.2')


def test_whole_subnet_maker_good():
    assert '192.168.1.0' == whole_subnet_maker('192.168.1.25', 24)


def test_number_check_good():
    assert number_check('5', return_number=False) == True


def test_number_check_bad():
    assert number_check('a', return_number=False) == False


def test_ip_good():
    assert ip('192.168.1.1', return_tuple=False) == True
    assert ip('224.1.1.1', return_tuple=False) == True


def test_ip_bad():
    assert ip('192.168.1.1000', return_tuple=False) == False


def test_subnet_range():
    assert subnet_range('192.168.1.1', 24) == {'RANGE': '192.168.1.1 to 192.168.1.254',
                                               'CIDRVAL': '24',
                                               'NET': '192.168.1.0',
                                               'CIDR': '192.168.1.0/24',
                                               'BCAST': '192.168.1.255',
                                               'MASK': '255.255.255.0',
                                               'IP': '192.168.1.1',
                                               'INVMASK': '0.0.0.255'}


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