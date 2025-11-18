@Test
void invalid_command_is_stored() {
    masterControl.executeCommand("create checking 123456789 1.0"); 
    assertThat(invalidStorage.getInvalidCommands()).containsExactly("create checking 123456789 1.0");
    assertThat(bank.getAccounts()).isEmpty();
}

@Test
void valid_create_checking_is_processed_and_not_stored() {
    masterControl.executeCommand("create checking 12345678 2.5");
    assertThat(bank.getAccounts()).hasSize(1);
    assertThat(invalidStorage.getInvalidCommands()).isEmpty();
}

@Test
void valid_deposit_is_processed_and_not_stored() {
    masterControl.executeCommand("create savings 11111111 0.1");
    masterControl.executeCommand("deposit 11111111 1000");

}