/*
##########################################################
# Script Name: IpAddressTools.groovy                     #
# Script Type: Groovy                                    #
# Updated By: Benjamin P. Trachtenberg                   #
# Date Written 6/15/2016                                 #
#                                                        #
# Description:                                           #
# Collection of tools for IP Address's                   #
# This was created from my Python version of the same    #
# tools                                                  #
#                                                        #
##########################################################
Version: 1.2.0
Author: Benjamin P. Trachtenberg
Contact: e_ben_75-python@yahoo.com
*/
import java.util.regex.Matcher

class IpAddressTools {
    public mask_conversion = [0: ["OCT1": 0, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "0.0.0.0", "INVMASK": "255.255.255.255", "CIDR": "0"],
                              1: ["OCT1": 128, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "128.0.0.0", "INVMASK": "127.255.255.255", "CIDR": "1"],
                              2: ["OCT1": 192, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "192.0.0.0", "INVMASK": "63.255.255.255", "CIDR": "2"],
                              3: ["OCT1": 224, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "224.0.0.0", "INVMASK": "31.255.255.255", "CIDR": "3"],
                              4: ["OCT1": 240, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "240.0.0.0", "INVMASK": "15.255.255.255", "CIDR": "4"],
                              5: ["OCT1": 248, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "248.0.0.0", "INVMASK": "7.255.255.255", "CIDR": "5"],
                              6: ["OCT1": 252, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "252.0.0.0", "INVMASK": "3.255.255.255", "CIDR": "6"],
                              7: ["OCT1": 254, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "254.0.0.0", "INVMASK": "1.255.255.255", "CIDR": "7"],
                              8: ["OCT1": 255, "OCT2": 0, "OCT3": 0, "OCT4": 0, "MASK": "255.0.0.0", "INVMASK": "0.255.255.255", "CIDR": "8"],
                              9: ["OCT1": 255, "OCT2": 128, "OCT3": 0, "OCT4": 0, "MASK": "255.128.0.0", "INVMASK": "0.127.255.255", "CIDR": "9"],
                              10: ["OCT1": 255, "OCT2": 192, "OCT3": 0, "OCT4": 0, "MASK": "255.192.0.0", "INVMASK": "0.63.255.255", "CIDR": "10"],
                              11: ["OCT1": 255, "OCT2": 224, "OCT3": 0, "OCT4": 0, "MASK": "255.224.0.0", "INVMASK": "0.31.255.255", "CIDR": "11"],
                              12: ["OCT1": 255, "OCT2": 240, "OCT3": 0, "OCT4": 0, "MASK": "255.240.0.0", "INVMASK": "0.15.255.255", "CIDR": "12"],
                              13: ["OCT1": 255, "OCT2": 248, "OCT3": 0, "OCT4": 0, "MASK": "255.248.0.0", "INVMASK": "0.7.255.255", "CIDR": "13"],
                              14: ["OCT1": 255, "OCT2": 252, "OCT3": 0, "OCT4": 0, "MASK": "255.252.0.0", "INVMASK": "0.3.255.255", "CIDR": "14"],
                              15: ["OCT1": 255, "OCT2": 254, "OCT3": 0, "OCT4": 0, "MASK": "255.254.0.0", "INVMASK": "0.1.255.255", "CIDR": "15"],
                              16: ["OCT1": 255, "OCT2": 255, "OCT3": 0, "OCT4": 0, "MASK": "255.255.0.0", "INVMASK": "0.0.255.255", "CIDR": "16"],
                              17: ["OCT1": 255, "OCT2": 255, "OCT3": 128, "OCT4": 0, "MASK": "255.255.128.0", "INVMASK": "0.0.127.255", "CIDR": "17"],
                              18: ["OCT1": 255, "OCT2": 255, "OCT3": 192, "OCT4": 0, "MASK": "255.255.192.0", "INVMASK": "0.0.63.255", "CIDR": "18"],
                              19: ["OCT1": 255, "OCT2": 255, "OCT3": 224, "OCT4": 0, "MASK": "255.255.224.0", "INVMASK": "0.0.31.255", "CIDR": "19"],
                              20: ["OCT1": 255, "OCT2": 255, "OCT3": 240, "OCT4": 0, "MASK": "255.255.240.0", "INVMASK": "0.0.15.255", "CIDR": "20"],
                              21: ["OCT1": 255, "OCT2": 255, "OCT3": 248, "OCT4": 0, "MASK": "255.255.248.0", "INVMASK": "0.0.7.255", "CIDR": "21"],
                              22: ["OCT1": 255, "OCT2": 255, "OCT3": 252, "OCT4": 0, "MASK": "255.255.252.0", "INVMASK": "0.0.3.255", "CIDR": "22"],
                              23: ["OCT1": 255, "OCT2": 255, "OCT3": 254, "OCT4": 0, "MASK": "255.255.254.0", "INVMASK": "0.0.1.255", "CIDR": "23"],
                              24: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 0, "MASK": "255.255.255.0", "INVMASK": "0.0.0.255", "CIDR": "24"],
                              25: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 128, "MASK": "255.255.255.128", "INVMASK": "0.0.0.127", "CIDR": "25"],
                              26: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 192, "MASK": "255.255.255.192", "INVMASK": "0.0.0.63", "CIDR": "26"],
                              27: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 224, "MASK": "255.255.255.224", "INVMASK": "0.0.0.31", "CIDR": "27"],
                              28: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 240, "MASK": "255.255.255.240", "INVMASK": "0.0.0.15", "CIDR": "28"],
                              29: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 248, "MASK": "255.255.255.248", "INVMASK": "0.0.0.7", "CIDR": "29"],
                              30: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 252, "MASK": "255.255.255.252", "INVMASK": "0.0.0.3", "CIDR": "30"],
                              31: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 254, "MASK": "255.255.255.254", "INVMASK": "0.0.0.1", "CIDR": "31"],
                              32: ["OCT1": 255, "OCT2": 255, "OCT3": 255, "OCT4": 255, "MASK": "255.255.255.255", "INVMASK": "0.0.0.0", "CIDR": "32"]]

    public ucast_ip_mask(ip_addr_and_mask, return_list=true) {
        def regex_ucast_ip_and_mask = ~/^((22[0-3])|(2[0-1][0-9])|(1[0-9][0-9])|([1-9]?[0-9])).((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9])).((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9])).((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\/((3[0-2])|([1-2]?[0-9]))$/
        Matcher matcher = ip_addr_and_mask =~ regex_ucast_ip_and_mask
        if (return_list){
            while (!matcher.matches()) {
                println('Not a good unicast IP and CIDR mask combo.')
                println('Please try again.')
                ip_addr_and_mask = this.get_cli_input('Please enter a IP address and mask in the follwing format x.x.x.x/x: ')
                matcher = ip_addr_and_mask =~ regex_ucast_ip_and_mask
            }
            return ip_addr_and_mask.split('/')
        } else if (!return_list) {
            return matcher.matches()
        }
    }

    public ucast_ip(ip_addr, return_good_ip=true) {
        def regex_ucast_ip = ~/^((22[0-3])|(2[0-1][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))$/
        Matcher matcher = ip_addr =~ regex_ucast_ip
        if (return_good_ip) {
            while (!matcher.matches()) {
                println('Not a good unicast IP.')
                println('Please try again.')
                ip_addr = this.get_cli_input('Please enter a unicast IP address in the following format x.x.x.x: ')
                matcher = ip_addr =~ regex_ucast_ip
            }
            return ip_addr
        } else if (!return_good_ip) {
            return matcher.matches()
        }
    }

    public mcast_ip_mask(ip_addr_and_mask, return_list=true) {
        def regex_mcast_ip_and_mask = /^(((2[2-3][4-9])|(23[0-3]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\/((3[0-2])|([1-2][0-9])|[3-9]))$/
        Matcher matcher = ip_addr_and_mask =~ regex_mcast_ip_and_mask
        if (return_list) {
            while (!matcher.matches()) {
                println('Not a good multicast IP and CIDR mask combo.')
                println('Please try again.')
                ip_addr_and_mask = this.get_cli_input('Please enter a multicast IP address and mask in the follwing format x.x.x.x/x: ')
                matcher = ip_addr_and_mask =~ regex_mcast_ip_and_mask
            }
            return ip_addr_and_mask.split('/')
        } else if (!return_list) {
            return matcher.matches()
        }
    }

    public mcast_ip(ip_addr, return_good_ip=true) {
        def regex_mcast_ip = /^(((2[2-3][4-9])|(23[0-3]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9])))$/
        Matcher matcher = ip_addr =~ regex_mcast_ip
        if (return_good_ip) {
            while (!matcher.matches()) {
                println('Not a good multicast IP.')
                println('Please try again.')
                ip_addr = this.get_cli_input('Please enter a multicast IP address in the following format x.x.x.x: ')
                matcher = ip_addr =~ regex_mcast_ip
            }
            return ip_addr
        } else if (!return_good_ip) {
            return matcher.matches()
        }
    }

    public cidr_check(cidr, return_cidr=true) {
        def good_cidr
        try {
            if (cidr.toInteger() < 0 | cidr.toInteger() > 32) {
                good_cidr = false
            } else {
                good_cidr = true
            }

            if (return_cidr) {
                while (!good_cidr) {
                    println('Sorry the CIDR value $cidr is not a valid value must be a value of 0 to 32.  Please try again.')
                    cidr = this.get_cli_input('What is the mask for in CIDR format?: ')
                    if (cidr.toInteger() < 0 | cidr.toInteger() > 32) {
                        good_cidr = false
                    } else {
                        good_cidr = true
                    }
                }
                return cidr
            } else if (!return_cidr) {
                return good_cidr
            }

        } catch (e) {
            println(e)
        }
    }

    public get_neighbor_ip(ip_addr, cidr='30') {
        def our_octet
        def neighbor_octet
        def ranger
        def out_ip_addr = new String()
        def neighbor_ip_addr = new String()
        try {
            def ip_addr_split = ip_addr.tokenize('.')
            def max_counter = 0
            if (cidr.toInteger() == 30) {
                ranger = 4
            } else if (cidr.toInteger() == 31) {
                ranger = 2
            }

            while (max_counter < 256) {
                try {
                    if (ip_addr_split[3].toInteger() >= max_counter && ip_addr_split[3].toInteger() < (max_counter + ranger)) {
                        if (ranger == 4) {
                            our_octet = max_counter + 1
                            neighbor_octet = max_counter + 2
                            break
                        } else if (ranger == 2) {
                            our_octet = max_counter
                            neighbor_octet = max_counter + 1
                            break
                        }
                    }
                    max_counter += ranger
                } catch (e) {
                    println('The mask between the neighbors must be 30, or 31')
                    println(e)
                    System.exit(0)
                }
            }

            if (ip_addr_split[3] == our_octet) {
                out_ip_addr = "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$our_octet"
                neighbor_ip_addr = "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$neighbor_octet"
            } else if (ip_addr_split[3].toInteger() == neighbor_octet) {
                neighbor_ip_addr = "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$our_octet"
                out_ip_addr = "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$neighbor_octet"
            } else {
                out_ip_addr = "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$our_octet"
                neighbor_ip_addr = "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$neighbor_octet"
            }
            return [out_ip_addr, neighbor_ip_addr]

        } catch (e) {
            println(e)
            System.exit(0)
        }
    }

    public whole_subnet_maker (ip_addr, cidr) {
        if (!this.ucast_ip(ip_addr, false) && !this.mcast_ip(ip_addr, false)) {
            println('Not a good ipv4 address')
            System.exit(0)
        }

        if (!cidr_check(cidr, false)) {
            println('Not a good CIDR value should be 0 to 32')
            System.exit(0)
        }

        def ip_addr_split = ip_addr.tokenize('.')

        if (cidr.toInteger() >= 24) {
            def octet = this.subnet_corrector(ip_addr_split[3], cidr)
            return "${ip_addr_split[0]}.${ip_addr_split[1]}.${ip_addr_split[2]}.$octet"
        } else if (cidr.toInteger() >= 16) {
            def octet = this.subnet_corrector(ip_addr_split[2], cidr)
            return "${ip_addr_split[0]}.${ip_addr_split[1]}.$octet.0"
        } else if (cidr.toInteger() >= 8) {
            def octet = this.subnet_corrector(ip_addr_split[1], cidr)
            return "${ip_addr_split[0]}.$octet.0.0"
        } else if (cidr.toInteger() >= 1) {
            def octet = this.subnet_corrector(ip_addr_split[0], cidr)
            return "$octet.0.0.0"
        } else {
            return '0.0.0.0'
        }

    }

    public subnet_range(ip_net, cidr) {
        def subnets_dict = [:]
        def subnet = this.whole_subnet_maker(ip_net, cidr)
        subnets_dict['IP'] = ip_net
        subnets_dict['NET'] = subnet
        subnets_dict['CIDR'] = "$subnet/$cidr"

        if (cidr.toInteger() >= 24) {
            def subnet_split = subnet.tokenize('.')
            def first_ip = subnet_split[3].toInteger() + 1
            def last_ip = (subnet_split[3].toInteger() + 1) + (253 - mask_conversion[cidr.toInteger()]['OCT4'].toInteger())
            def bcast_ip = (subnet_split[3].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT4'].toInteger())
            def temp = "${subnet_split[0]}.${subnet_split[1]}.${subnet_split[2]}."
            subnets_dict['RANGE'] = "$temp$first_ip to $temp$last_ip"
            subnets_dict['BCAST'] = "$temp$bcast_ip"
            subnets_dict['MASK'] = mask_conversion[cidr.toInteger()]['MASK']
            subnets_dict['INVMASK'] = mask_conversion[cidr.toInteger()]['INVMASK']
            subnets_dict['CIDRVAL'] = mask_conversion[cidr.toInteger()]['CIDR']
        } else if (cidr.toInteger() >= 16) {
            def subnet_split = subnet.tokenize('.')
            def first_ip = subnet_split[2].toInteger()
            def last_ip = (subnet_split[2].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT3'].toInteger())
            def bcast_ip = (subnet_split[2].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT3'].toInteger())
            def temp = "${subnet_split[0]}.${subnet_split[1]}."
            subnets_dict['RANGE'] = "$temp$first_ip.1 to $temp$last_ip.254"
            subnets_dict['BCAST'] = "$temp$bcast_ip.255"
            subnets_dict['MASK'] = mask_conversion[cidr.toInteger()]['MASK']
            subnets_dict['INVMASK'] = mask_conversion[cidr.toInteger()]['INVMASK']
            subnets_dict['CIDRVAL'] = mask_conversion[cidr.toInteger()]['CIDR']
        } else if (cidr.toInteger() >= 8) {
            def subnet_split = subnet.tokenize('.')
            def first_ip = subnet_split[1].toInteger()
            def last_ip = (subnet_split[1].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT2'].toInteger())
            def bcast_ip = (subnet_split[1].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT2'].toInteger())
            def temp = "${subnet_split[0]}."
            subnets_dict['RANGE'] = "$temp$first_ip.0.1 to $temp$last_ip.255.254"
            subnets_dict['BCAST'] = "$temp$bcast_ip.255.255"
            subnets_dict['MASK'] = mask_conversion[cidr.toInteger()]['MASK']
            subnets_dict['INVMASK'] = mask_conversion[cidr.toInteger()]['INVMASK']
            subnets_dict['CIDRVAL'] = mask_conversion[cidr.toInteger()]['CIDR']
        } else if (cidr.toInteger() >= 1) {
            def subnet_split = subnet.tokenize('.')
            def first_ip = subnet_split[0].toInteger()
            def last_ip = (subnet_split[0].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT1'].toInteger())
            def bcast_ip = (subnet_split[0].toInteger() + 1) + (254 - mask_conversion[cidr.toInteger()]['OCT1'].toInteger())
            def temp = "${subnet_split[0]}."
            subnets_dict['RANGE'] = "$temp$first_ip.0.0.1 to $temp$last_ip.255.255.254"
            subnets_dict['BCAST'] = "$temp$bcast_ip.255.255.255"
            subnets_dict['MASK'] = mask_conversion[cidr.toInteger()]['MASK']
            subnets_dict['INVMASK'] = mask_conversion[cidr.toInteger()]['INVMASK']
            subnets_dict['CIDRVAL'] = mask_conversion[cidr.toInteger()]['CIDR']
        }
        return subnets_dict
    }

    public all_subnets_possible(ip_net, cidr) {
        return this.all_subnets_longer_prefix(ip_net, cidr)
    }

    public all_subnets_longer_prefix(ip_net, cidr) {
        def subnets_list = []
        while (cidr.toInteger() <= 32) {
            try {
                subnets_list.add("${this.whole_subnet_maker(ip_net, cidr)}/${cidr}")
            } catch (e) {
                // Do nithing
            }
            cidr = (cidr.toInteger() + 1).toString()
        }
        return subnets_list
    }

    public all_subnets_shorter_prefix(ip_net, cidr, include_default=false) {
        def subnets_list = []
        if (include_default) {
            while (cidr.toInteger() >= 0) {
                try {
                    subnets_list.add("${this.whole_subnet_maker(ip_net, cidr)}/$cidr")
                } catch (e) {
                    // Do nothing
                }
                cidr = (cidr.toInteger() - 1).toString()
            }
        } else {
            while (cidr.toInteger() > 0) {
                try {
                    subnets_list.add("${this.whole_subnet_maker(ip_net, cidr)}/$cidr")
                } catch (e) {
                    // Do nothing
                }
                cidr = (cidr.toInteger() - 1).toString()
            }
        }

        return subnets_list
    }

    public number_check(check, return_number=true) {
        def good = false

        try {
            check.toInteger()
            good = true
        } catch (e) {
            good = false
        }

        if (return_number) {
            while (!good) {
                println('That is not a number.')
                println('Please try again.')
                check = get_cli_input('Please enter a number?: ')
                try {
                    check.toInteger()
                    good = true
                } catch (e) {
                    good = false
                }
            }

            return check
        } else {
            return good
        }
    }

    private subnet_corrector( octet, cidr ) {
        def cidr_int = cidr.toInteger()
        def octet_int = octet.toInteger()

        if (cidr_int >= 24) {
            cidr_int = mask_conversion[cidr_int]['OCT4']
        } else if (cidr_int >= 16) {
            cidr_int = mask_conversion[cidr_int]['OCT3']
        } else if (cidr_int >= 8) {
            cidr_int = mask_conversion[cidr_int]['OCT2']
        } else if (cidr_int >= 1) {
            cidr_int = mask_conversion[cidr_int]['OCT1']
        }
        def cidr_count = 0
        def cidr_v = 256 - cidr_int
        def cidr_2 = 256 - cidr_int

        while (cidr_count < 300) {
            if (octet_int >= cidr_count && octet_int <= cidr_2) {
                cidr_int = cidr_count
            }
            cidr_count = cidr_2
            cidr_2 = cidr_2 + cidr_v
        }

        return cidr_int.toString()
    }

    private get_cli_input(prompt) {
        print prompt
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        String input = br.readLine()
        return input
    }
}
