'''
COPYRIGHT Ericsson 2019
The copyright to the computer program(s) herein is the property of
Ericsson Inc. The programs may be used and/or copied only with written
permission from Ericsson Inc. or in accordance with the terms and
conditions stipulated in the agreement/contract under which the
program(s) have been supplied.

@summary:   Integration test for XXX
'''

from litp_generic_test import GenericTest, attr
import test_constants


class StoryXXX(GenericTest):
    """
    DELETE THIS TEST
    """

    def setUp(self):
        """Run before every test"""
        super(StoryXXX, self).setUp()

    def tearDown(self):
        """Run after every test"""
        super(StoryXXX, self).tearDown()

    @attr('all', 'cdb_tmp')
    def test_01_p_xxx(self):
        """
            Descritpion:
            Actions:
                1.
            Result:
        """

        print "DELETE ME AND REPLACE WITH A REAL LITP TEST"
        self.assertEqual([], [])
