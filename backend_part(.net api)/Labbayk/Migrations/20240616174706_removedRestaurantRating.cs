using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Labbayk.Migrations
{
    /// <inheritdoc />
    public partial class removedRestaurantRating : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Rating",
                table: "Restaurants");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "Rating",
                table: "Restaurants",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }
    }
}
