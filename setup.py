from setuptools import setup
import os
from ipaddresstools.ipaddresstools import __version__

base_dir = os.path.abspath(os.path.dirname(__file__))
with open(os.path.join(base_dir, 'README.md'), encoding='utf-8') as f:
    long_description = f.read()

packages = [
    'ipaddresstools'
]

tests_require = [
    'pytest',
]

setup(
    name='ipaddresstools',
    version=__version__,
    python_requires='~=3.3',
    description='This is a library used to manipulate and verify ipv4 address\'s. ucast and mcast',
    long_description=long_description,
    long_description_content_type='text/markdown',
    keywords='ipv4 ip multicast unicast network engineer',
    url='https://ipaddresstools.readthedocs.io',
    author='Benjamin P. Trachtenberg',
    author_email='e_ben_75-python@yahoo.com',
    license='MIT',
    packages=packages,
    include_package_data=True,
    test_suite='pytest',
    tests_require=tests_require,
    classifiers=[
        'Development Status :: 5 - Production/Stable',
        'Intended Audience :: Developers',
        'License :: OSI Approved :: MIT License',
        'Programming Language :: Python :: 3',
    ],
)
