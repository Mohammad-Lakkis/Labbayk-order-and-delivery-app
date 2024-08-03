using Labbayk.Models;
using Microsoft.EntityFrameworkCore;

namespace Labbayk.Data
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {

        }

        public DbSet<Category> Categories { get; set; }
        public DbSet<Item> Items { get; set; }
        public DbSet<Price> PriceRanges { get; set; }
        public DbSet<Restaurant> Restaurants { get; set; }
        public DbSet<Time> TimeRanges { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<OrderItem> OrderItems { get; set; }
        public DbSet<Driver> Drivers { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<OrderItem>()
                .HasKey(oi => new { oi.OrderId, oi.ItemId });
            modelBuilder.Entity<OrderItem>()
                .HasOne(o => o.Order)
                .WithMany(oi => oi.OrderItems)
                .HasForeignKey(o => o.OrderId);
            modelBuilder.Entity<OrderItem>()
                .HasOne(i => i.Item)
                .WithMany(oi => oi.OrderItems)
                .HasForeignKey(i => i.ItemId);
        }


    }
}
